package jkl.crossplatform.game.tangram;

import com.badlogic.gdx.graphics.Texture;

import jkl.crossplatform.game.tangram.shapes.TangramParallSprite2;
import jkl.crossplatform.game.tangram.shapes.TangramQuadSprite;
import jkl.crossplatform.game.tangram.shapes.TangramSprite;
import jkl.crossplatform.game.tangram.shapes.TangramTriangleLarge;
import jkl.crossplatform.game.tangram.shapes.TangramTriangleMedium;
import jkl.crossplatform.game.tangram.shapes.TangramTriangleSmall;

public class TangramForm {
	String[] shape = new String[7];
	int[] rotation = new int[7];
	int[] distanceX = new int[6];
	int[] distanceY = new int[6];
	
	public static TangramForm toForm(TangramSprite[] ts) {
		TangramForm result = new TangramForm();
	    for (int index=0; index< ts.length; index++) {
    		result.shape[index] = ts[index].name;
    		result.rotation[index] = (int) ts[index].getRotation();;
	    	if (index < ts.length-1) {
	    		int x= (int) Math.round(ts[index+1].getX()) - Math.round(ts[index].getX());
	    		result.distanceX[index] = x;
	    		int y= (int) Math.round(ts[index+1].getY()) - Math.round(ts[index].getY());
//	        	int y =(int) (vtcenter[index+1].y-vtcenter[index].y);
	        	result.distanceY[index] = y;
	    	}
		 }	
	    return result;
	}

	public static TangramForm toForm(String form) {
		TangramForm result = new TangramForm();
	    String[] p =form.split(":");
	    int pindex=0;
	    for (int index=0;index < 7;index++) {
	    	result.shape[index] = p[pindex];
	    	result.rotation[index] = Integer.parseInt(p[pindex+1]);
	    	if (index < 6) {
	    		int x= Integer.parseInt(p[pindex+2]);
	    		result.distanceX[index] = x;
	        	int y = Integer.parseInt(p[pindex+3]);
	        	result.distanceY[index] = y;
	    	}
		pindex+=4; 
	    }
	    return result;
	}
	
	public TangramSprite[] toGoalSprite(Texture t,float scale) {
//		TangramSprite[] result = new TangramSprite[shape.length];
		TangramSprite[] result = new TangramSprite[7];
		for (int index=0;index< shape.length; index++) {
		    if (shape[index].equals("QUAD")) { result[index] = new TangramQuadSprite(t); }
		    if (shape[index].equals("PARALLEL")) { result[index] = new TangramParallSprite2(t); }
		    if (shape[index].equals("TRIANGLE_SMALL")) { result[index] = new TangramTriangleSmall(t); }
		    if (shape[index].equals("TRIANGLE_MEDIUM")) { result[index] = new TangramTriangleMedium(t); }
		    if (shape[index].equals("TRIANGLE_LARGE")) { result[index] = new TangramTriangleLarge(t); }
		    if (index==0) {
		    	int x =(int) distanceX[index];
		    	if (x<0) {
		    		result[index].setPosition(TangramSprite.BaseLength,TangramSprite.BaseLength/2);
		    	   }
		    	else {
		    		result[index].setPosition(TangramSprite.BaseLength,TangramSprite.BaseLength/2);
		    	   }
		    	}
		    else {
		    	int x =(int) ((int) distanceX[index-1]*scale);
		    	int y =(int) ((int) distanceY[index-1]*scale);
		    	result[index].setPosition(result[index-1].getX()+x,result[index-1].getY()+y);
		    }
		    result[index].setRotation(rotation[index]);
		    result[index].setScale(scale);
	    }
		return result;
	}

	public boolean equals(TangramForm tf) {
		for (int i=0;i<shape.length; i++) {
			if (! shape[i].equals(tf.shape[i])) return false;
		}
		for (int i=0;i<rotation.length; i++) {
			if (rotation[i] != tf.rotation[i]) return false;
		}
		for (int i=0;i < distanceX.length; i++) {
			if (Math.abs(distanceX[i]-tf.distanceX[i])>2) return false;
		}
		for (int i=0;i < distanceY.length; i++) {
			if (Math.abs(distanceY[i]-tf.distanceY[i])>2) return false;
		}
		return true;
	}
	
	public String toString() {
	    String result = "";
	    for (int index=0;index < 7;index++) {
	    	result += shape[index];
	    	result += ":"+rotation[index];
	    	if (index < 6) {
	    		result += ":"+distanceX[index];
	    		result += ":"+distanceY[index]+":";
	    	}
		 }
		return result;
	}
}
