package jkl.crossplatform.game.tangram.shapes;

import com.badlogic.gdx.graphics.Texture;

public class TangramQuadSprite extends TangramSprite {
	
	static float l = TangramSprite.BaseLength;
/*	
 * corner vertices from center 
 */	
	static float[] vertices = new float[] {
	        -0.5f*l, -0.5f*l,   //A
             0.5f*l, -0.5f*l,   //B
	         0.5f*l,  0.5f*l,   //C
	        -0.5f*l,  0.5f*l,   //D
	    };
	
	public TangramQuadSprite(Texture texture) {
		super(vertices, texture);
		name = "QUAD";
	}
	
}
