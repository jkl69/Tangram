package jkl.crossplatform.game.tangram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jkl.crossplatform.game.tangram.shapes.TangramParallSprite2;
import jkl.crossplatform.game.tangram.shapes.TangramQuadSprite;
import jkl.crossplatform.game.tangram.shapes.TangramSprite;
import jkl.crossplatform.game.tangram.shapes.TangramTriangleLarge;
import jkl.crossplatform.game.tangram.shapes.TangramTriangleMedium;
import jkl.crossplatform.game.tangram.shapes.TangramTriangleSmall;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

public class Tamgramm implements ApplicationListener {
    
	public float[] vertices,vtmp,vt; 
	 
	Texture textureBlack, textureBlue,textureRed;
	PolygonSpriteBatch polyBatch;
	TangramSprite[] tgoal;// = new TangramSprite[7];
	TangramSprite[] tsprite = new TangramSprite[7];
	TangramSprite[] torder = new TangramSprite[7];
	TangramSprite ts,tt;
    ShapeRenderer shapeRenderer;
    Circle TurnR,TurnL;
    Random random = new Random();
    boolean debugrender=false;
    SpriteBatch spriteBatch;
    BitmapFont font ;
    String formString = null;
    TangramForm TARGETFORM ;//= new TangramForm();
    TangramForm FORM;// = new TangramForm();
    List<String> lines = new ArrayList<String>();
    
    private void createGoal(int goal) {
	    float scale=.25f;
//	    TARGETFORM = TangramForm.toForm(formString);
	    TARGETFORM = TangramForm.toForm(lines.get(goal));
	    tgoal = TARGETFORM.toGoalSprite(textureRed, scale);
    }
    
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
	    spriteBatch = new SpriteBatch();
		
		TangramSprite.BaseLength = w / 10f;
		
	    shapeRenderer =new ShapeRenderer();
		new OrthographicCamera(w,h);
		
	    TurnL = new Circle(w-5, h/2f, 15);
	    TurnR = new Circle(5, h/2f, 15);
	    
	    Gdx.input.setInputProcessor(new TangramInputAdapter(this));
	    
	    textureBlack = makeTextureBox(Color.BLACK); 
	    textureBlue = makeTextureBox(Color.BLUE); 
	    textureRed = makeTextureBox(Color.RED); 
	    
	    readFormFromFile();
	    createGoal(random.nextInt(lines.size()));
	    
	    ts = new TangramQuadSprite(textureBlack);
	    ts.setPosition(random.nextInt(800), 400);
	    ts.setRotation(random.nextInt(1)*45);
	    tsprite[0] = ts;

	    ts = new TangramParallSprite2(textureBlack);
	    ts.setPosition(random.nextInt(800), 436);
	    ts.setRotation(random.nextInt(4)*45);
	    tsprite[1] = ts;
	    
	    ts = new TangramTriangleSmall(textureBlack);
//	    ts.name = ts.name+"_1";
	    ts.setPosition(random.nextInt(800), 136);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[2] = ts;
	    
//	    ts = new TangramTriangleSmallSprite(textureRed);
	    ts = new TangramTriangleSmall(textureBlack);
//	    ts.name = ts.name+"_2";
	    ts.setPosition(random.nextInt(800), 336);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[3] = ts;

	    ts = new TangramTriangleMedium(textureBlack);
	    ts.setPosition(random.nextInt(800), 265);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[4] = ts;

	    ts = new TangramTriangleLarge(textureBlack);
//	    ts.name = ts.name+"_1";
	    ts.setPosition(random.nextInt(800), 565);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[5] = ts;
	    
	    ts = new TangramTriangleLarge(textureBlack);
//	    ts.name = ts.name+"_2";
	    ts.setPosition(random.nextInt(800), 65);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[6] = ts;
	    	    
	    font = new BitmapFont();
	    polyBatch = new PolygonSpriteBatch();
//	    polyBatch.setProjectionMatrix(cam.combined);
	}
	
	public void sort(){
		torder = Arrays.copyOf(tsprite,tsprite.length);
		int lenD = torder.length;
		  int j = 0;
		  TangramSprite tmp = null;
		  for(int i=0;i<lenD;i++){
		    j = i;
		    for(int k = i;k<lenD;k++){
		      if(torder[j].getY() > torder[k].getY()){ j = k; }
		      if(torder[j].getY() == torder[k].getY()){
			      if(torder[j].getX() > torder[k].getX()){ j = k; }
		      }   
		    }
		    tmp = torder[i];
		    torder[i] = torder[j];
		    torder[j] = tmp;
		  }

    	for (TangramSprite it : torder) {
			it.setPosition(Math.round(it.getX()),Math.round(it.getY()));
		 }	

    	if (isSolved()) {
	    	debugrender=true;
	    }
	}
  private boolean isSolved() {
	System.out.println("Solved?");
	System.out.println("T:"+TARGETFORM.toString());
	FORM = TangramForm.toForm(torder);
	System.out.println("R:"+FORM.toString());
    if (TARGETFORM.equals(FORM)) {	
    	System.out.println("YES");
    	return true;
    	}
	return false;
  }
  

  public void printorder() {
	String txt= FORM.toString();  
	System.out.println(txt);
	FileWriter f0;
	try {
//		f0 = new FileWriter("/assets/data/forms.txt");
		f0 = new FileWriter("nforms.txt");
	    String newLine = System.getProperty("line.separator");
	    f0.write(txt+ newLine);
	    f0.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

  public void DebugRender() {
        shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 1, 1);
//		shapeRenderer.line(0, 0, vo.x, vo.y);
		for (int index=0;index< torder.length-1; index++) {
        	shapeRenderer.line(torder[index].getX(),torder[index].getY(),
        			           torder[index+1].getX(),torder[index+1].getY());
//        	shapeRenderer.line(vo,vdd);
//           	CharSequence str = String.valueOf(vdiff[index].x+":"+vdiff[index].y);
        	int x= (int) (torder[index+1].getX() - torder[index].getX());
        	int y =(int) (torder[index+1].getY() - torder[index].getY());
        	String str = x+":"+y;
            spriteBatch.begin();
//            font.draw(spriteBatch, str, vo.x+(vdiff[index].x/2f), vo.y+(vdiff[index].y/2f));
            font.draw(spriteBatch, str, torder[index].getX()+x/2f,torder[index].getY()+y/2f);
            spriteBatch.end();	    	}

		shapeRenderer.end();
		}
	
     
   private void readFormFromFile() {
	   FileReader fileReader = null;
	   try {
		   fileReader = new FileReader(new File("forms.txt"));
	   } catch (FileNotFoundException e) {
		  e.printStackTrace();
		  return;
	   }
	   BufferedReader br = new BufferedReader(fileReader);
	   formString = null;
	   // if no more lines the readLine() returns null
	   try {
//		   formString = br.readLine();
			while ((formString = br.readLine()) != null) {
		       lines .add(formString);
		       }
    	} catch (IOException e) {
	    	e.printStackTrace();
	   }
	System.out.println("Form:"+formString);
    }
   
	private Texture makeTextureBox(Color c) {
		Pixmap pixmap = new Pixmap( 16, 16, Format.RGBA8888 );
//		pixmap.setColor( 0, 1, 0, 0.75f );
		pixmap.setColor(c);
		pixmap.fill();
		Texture result = new Texture( pixmap );
		pixmap.dispose();
		return result;
	}

	@Override
	public void dispose() {
		polyBatch.dispose();
		textureBlue.dispose();
		shapeRenderer.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void render() {		
//		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
//	    polyBatch.begin();  tgoal[0].draw(polyBatch);	    polyBatch.end();

	    for (TangramSprite it : tgoal) {
		    polyBatch.begin(); 	it.draw(polyBatch);  polyBatch.end();
	    }
	    for (TangramSprite it : tsprite) {
		    polyBatch.begin(); 	it.draw(polyBatch);  polyBatch.end();
	    }

	    for (TangramSprite it : tsprite) {
		    it.RenderX(shapeRenderer);
	    }

	    shapeRenderer.begin(ShapeType.Filled);
//	    shapeRenderer.setColor(1, 0, 0, 1);
	    shapeRenderer.setColor(1, 1, 0, 1);
	    shapeRenderer.circle(TurnR.x,TurnR.y,TurnR.radius);
	    shapeRenderer.circle(TurnL.x,TurnL.y,TurnL.radius);
	    shapeRenderer.end();
	    if (debugrender) {
	    	DebugRender();
	    }

	}
	

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
