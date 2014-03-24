package jkl.crossplatform.game.tangram.shapes;

import com.badlogic.gdx.graphics.Texture;

public class TangramTriangleSmall extends TangramSprite {
/*           
 *              B
 *            /  |        
 *        c /    |              
 *        /      |a              
 *      /        |              
 *    A --- b --- C                
 *                                
 *   a=b       
 *                            
 */
	static float b = TangramSprite.BaseLength;
	
	static float Ax = -(2*b)/3;
	static float Bx = (b-(2*b)/3);
	static float Cx = (b-(2*b)/3);
	static float Ay= -(b/3);
	static float By = -(b/3);
	static float Cy = b-(b/3);
	
/*
 * corner vertices from center 
 */	
	static float[] vertices = new float[] {
	        Ax, Ay,   //A
            Bx, By,   //B
	        Cx, Cy,   //C
	    };
	
	public TangramTriangleSmall(Texture texture) {
		super(vertices, texture);
		name = "TRIANGLE_SMALL";
	}
	
}
