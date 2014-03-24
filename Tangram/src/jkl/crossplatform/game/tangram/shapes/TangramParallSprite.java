package jkl.crossplatform.game.tangram.shapes;

import com.badlogic.gdx.graphics.Texture;

public class TangramParallSprite extends TangramSprite {
/*           
 *            
 *      D -- c -- C         TA--Ta--TC
 *      / \     /             |    /
 *     d   f   b             Tb  Tc
 *    /     \ /               |  /
 *  A --a -- B                | / 
 *                            TB
 *                            
 *   a=c    d=f=b            Ta=Tb   
 *                            
 */
	static float Tc = TangramSprite.BaseLength;
/*
 *   Ta^2 + Tb^2 = Tc^2
 */
	static float Tb = (float) Math.sqrt(Math.pow(Tc,2)/ 2.0f);
	static float a = (float) Math.sqrt(2*Math.pow(Tc,2));
/*	
 * corner vertices from center 
 */	
	static float[] vertices = new float[] {
	        -0.5f*a-0.5f*Tb, -0.5f*Tb,   //A
             0.5f*a-0.5f*Tb, -0.5f*Tb,   //B
	         0.5f*a+0.5f*Tb,  0.5f*Tb,   //C
	        -0.5f*a+0.5f*Tb,  0.5f*Tb,   //D
	    };
	
	public TangramParallSprite(Texture texture) {
		super(vertices, texture);
		name = "PARALLEL";
	}
	
}
