package jkl.crossplatform.game.tangram;

import jkl.crossplatform.game.tangram.shapes.TangramSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TangramInputAdapter extends InputAdapter {
  
  static float snapdistance=9f;
	//	private float[] vertices;
  private Array<Vector2> v2 = new Array();
  private Tamgramm tangram;
  TangramSprite tangramsprite;
  float ox,oy;
  
  private boolean isSnapDistance(Vector2 v) {
		for (TangramSprite it : tangram.tsprite) {
		    if (it != tangramsprite) {
		    	float[] shapev = it.polygon.getTransformedVertices();
//				System.out.println("ckeck distance to "+it.name);
			    for (int i=0 ;i< shapev.length; i+=2) {	
					Vector2 shpcorner= new Vector2(shapev[i],shapev[i+1]);
					shpcorner.sub(v);
//					float dist =v.dst(v2);
					float dist =shpcorner.len();
//					System.out.println("corner:"+i/2+" x"+shapev[i]+" Y:"+shapev[i+1]+" Dist:"+dist);
					if (dist<snapdistance) {
						tangramsprite.setPosition(tangramsprite.getX()+shpcorner.x,tangramsprite.getY()+shpcorner.y);
						return true;
					}
				}
		    }
		}
	return false;
  }
  
  private void checksnap() {
	  	float[] activshapevertices = tangramsprite.polygon.getTransformedVertices();
		    for (int i=0 ;i< activshapevertices.length; i+=2) {	
//		    	System.out.println("corner "+i/2+" activ shape "+tangramsprite.name);
		    	Vector2 v2= new Vector2(activshapevertices[i],activshapevertices[i+1]);
		    	if (isSnapDistance(v2)) {
		    		return ;
		    	}
		    }
	  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//	    int mY = Gdx.graphics.getHeight()-screenY;
		if (tangramsprite != null) {
			checksnap();
		}
		tangram.sort();
		return super.touchUp(screenX, screenY, pointer, button);
	}

	public TangramInputAdapter(Tamgramm t) {
		this.tangram =t ;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		Gdx.graphics.getHeight()
		int mY = Gdx.graphics.getHeight()-screenY;

		if (tangram.TurnL.contains(screenX, mY)) {
			if (tangramsprite != null) 
				tangramsprite.setRotation(tangramsprite.getRotation()-45);
			System.out.println("Rotation:"+tangramsprite.getRotation());
			return super.touchDown(screenX, screenY, pointer, button);
    	}
		if (tangram.TurnR.contains(screenX, mY)) {
			if (tangramsprite != null)
				tangramsprite.setRotation(tangramsprite.getRotation()+45);
			System.out.println("Rotation:"+tangramsprite.getRotation());
			return super.touchDown(screenX, screenY, pointer, button);
    	}
		
	    tangramsprite = null;
		
	    for (TangramSprite it : tangram.tsprite) {
				it.selected = false;
	       }
	    int len=tangram.tsprite.length-1;
	    for (int index=0;index<tangram.tsprite.length;index++) {
     		if (tangram.tsprite[index].contains(screenX, mY)) {
     			tangram.tsprite[index].selected = true;
		        tangramsprite = tangram.tsprite[index];
		        if (index<len) {  //bring selected to foreground
					System.out.println("Sprite_index:"+index);
		        	TangramSprite tmp = tangram.tsprite[len];
		        	tangram.tsprite[len] = tangram.tsprite[index];
		        	tangram.tsprite[index]=tmp;
		        }
			    ox= screenX;
			    oy= mY;
			    break;
				}
		}
		tangram.debugrender=false;
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean keyDown(int keycode) {
	 	if (keycode == Input.Keys.INSERT) {
			System.out.println("INSERT pressed");
			tangram.printorder();
			tangram.debugrender=true;
	 	}
		return super.keyDown(keycode);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int mY = Gdx.graphics.getHeight()-screenY;
		float diffx =screenX-ox;
		float diffy =mY-oy;
		if (tangramsprite!= null) {
//			System.out.println("Sprite_draggx:"+diffx+"draggy:"+diffy);
			tangramsprite.setPosition(tangramsprite.getX()+diffx,tangramsprite.getY()+diffy);
		    ox= screenX;
		    oy= mY;
		} else {
//			System.out.println("draggx:"+screenX+"draggy:"+mY);
		}
		return super.touchDragged(screenX, screenY, pointer);
	}

}
