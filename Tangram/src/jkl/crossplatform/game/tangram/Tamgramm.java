package jkl.crossplatform.game.tangram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import jkl.crossplatform.game.tangram.shapes.TangramParallSprite;
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
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Tamgramm implements ApplicationListener {
    
	public float[] vertices,vtmp,vt; 
	 
	Texture textureBlue,textureRed;
	PolygonSpriteBatch polyBatch;
	TangramSprite[] tsprite = new TangramSprite[7];
	TangramSprite[] torder = new TangramSprite[7];
	TangramSprite ts,tt;
    ShapeRenderer shapeRenderer;
    Circle TurnR,TurnL;
    Random random = new Random();
    boolean debugrender=false;
    Vector2[] vtcenter= new Vector2[7];
    SpriteBatch spriteBatch;
    BitmapFont font ;
    String FORM = null;

    
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
	    
	    textureBlue = makeTextureBox(Color.BLUE); 
	    textureRed = makeTextureBox(Color.RED); 
	    
        tt = new TangramQuadSprite(textureRed);
	    tt.setPosition(random.nextInt(800), 550);
	    tt.setScale(.4f);
	    
	    ts = new TangramQuadSprite(textureBlue);
	    ts.setPosition(random.nextInt(800), 400);
	    tsprite[0] = ts;

	    ts = new TangramParallSprite2(textureBlue);
	    ts.setPosition(random.nextInt(800), 436);
	    tsprite[1] = ts;
	    
	    ts = new TangramTriangleSmall(textureBlue);
//	    ts.name = ts.name+"_1";
	    ts.setPosition(random.nextInt(800), 136);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[2] = ts;
	    
//	    ts = new TangramTriangleSmallSprite(textureRed);
	    ts = new TangramTriangleSmall(textureBlue);
//	    ts.name = ts.name+"_2";
	    ts.setPosition(random.nextInt(800), 336);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[3] = ts;

	    ts = new TangramTriangleMedium(textureBlue);
	    ts.setPosition(random.nextInt(800), 265);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[4] = ts;

	    ts = new TangramTriangleLarge(textureBlue);
//	    ts.name = ts.name+"_1";
	    ts.setPosition(random.nextInt(800), 565);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[5] = ts;
	    
	    ts = new TangramTriangleLarge(textureBlue);
//	    ts.name = ts.name+"_2";
	    ts.setPosition(random.nextInt(800), 65);
	    ts.setRotation(random.nextInt(8)*45);
	    tsprite[6] = ts;
	    	    
	    font = new BitmapFont();
	    polyBatch = new PolygonSpriteBatch();
//	    polyBatch.setProjectionMatrix(cam.combined);
	    rr();
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

		for (int index=0;index<torder.length;index++) {
			vtcenter[index] = new Vector2(Math.round(torder[index].getX()),
					Math.round(torder[index].getY()));
		}

		if (isSolved()) {
	    	debugrender=true;
	    }
	}
  private boolean isSolved() {
	System.out.println("Solved?");
	return getForm().equals(FORM);
  }
  
  private String getForm() {
		String txt="";  
	    for (int index=0;index<torder.length;index++) {
	    	txt += torder[index].name;
	    	txt += ":"+torder[index].getRotation();
	    	if (index < vtcenter.length-1) {
	    		int x= (int) (vtcenter[index+1].x-vtcenter[index].x);
	        	int y =(int) (vtcenter[index+1].y-vtcenter[index].y);
	    		txt += ":"+x+":"+y+":";
	    	}
		 }	
//		System.out.println(txt);
		return txt;    
  }

  public void printorder() {
	String txt= getForm();  
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
//    for(i=0;i<10;i++)
//  {      f0.write("Result "+ i +" : "+ ans + newLine);     }
   }

  public void DebugRender() {
        shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 1, 1);
//		shapeRenderer.line(0, 0, vo.x, vo.y);
		for (int index=0;index<vtcenter.length-1;index++) {
        	shapeRenderer.line(vtcenter[index],vtcenter[index+1]);
//        	shapeRenderer.line(vo,vdd);
//           	CharSequence str = String.valueOf(vdiff[index].x+":"+vdiff[index].y);
        	int x= (int) (vtcenter[index+1].x-vtcenter[index].x);
        	int y =(int) (vtcenter[index+1].y-vtcenter[index].y);
        	String str = x+":"+y;
            spriteBatch.begin();
//            font.draw(spriteBatch, str, vo.x+(vdiff[index].x/2f), vo.y+(vdiff[index].y/2f));
            font.draw(spriteBatch, str, vtcenter[index].x+x/2f,vtcenter[index].y+y/2f);
            spriteBatch.end();	    	}

		shapeRenderer.end();
		}
	
   private void rr() {
	   FileReader fileReader = null;
	   try {
		   fileReader = new FileReader(new File("forms.txt"));
	   } catch (FileNotFoundException e) {
		  e.printStackTrace();
		  return;
	   }
	   BufferedReader br = new BufferedReader(fileReader);
	   FORM = null;
	   // if no more lines the readLine() returns null
	   try {
		   FORM = br.readLine();
//			while ((line = br.readLine()) != null) {     }
    	} catch (IOException e) {
	    	e.printStackTrace();
	   }
	System.out.println("Form:"+FORM);
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
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	    for (TangramSprite it : tsprite) {
		    polyBatch.begin();
	    	it.draw(polyBatch);
		    polyBatch.end();
	    }
	    polyBatch.begin();
    	tt.draw(polyBatch);
	    polyBatch.end();

	    for (TangramSprite it : tsprite) {
		    it.RenderX(shapeRenderer);
	    }

	    shapeRenderer.begin(ShapeType.Filled);
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
