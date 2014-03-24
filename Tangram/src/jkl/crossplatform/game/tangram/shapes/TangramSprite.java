package jkl.crossplatform.game.tangram.shapes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

public class TangramSprite {
	
	public static float BaseLength = 150;
	
	public String name;
	private PolygonRegion polyReg;
	protected PolygonSprite sprite;
	Texture texture;
	public Polygon polygon ;
	private Vector2 center;
	public Boolean selected= false;
	float[] vertices;
			
	public TangramSprite(float[] vertices,Texture texture) {
		
		this.vertices =vertices;
		this.texture = texture;
		
		ShortArray triangles  = new EarClippingTriangulator().computeTriangles(vertices);
	    polyReg = new PolygonRegion(new TextureRegion(texture),vertices,triangles.items);
		polygon =new Polygon(vertices);
		
	    sprite = new PolygonSprite(polyReg);
	    sprite.setOrigin(0,0);
	    polygon.setOrigin(0,0);
//	    System.out.println("origin:"+c.x+":"+c.y);
//	    sprite.setOrigin(150,130);
//	    polygon.setOrigin(150,130);
	}
	
	public void draw(PolygonSpriteBatch polyBatch) {
		sprite.draw(polyBatch);
	}

	public void RenderX(ShapeRenderer shapeRenderer) {
	    shapeRenderer.begin(ShapeType.Line);
	    shapeRenderer.setColor(1, 1, 1, 1);
	    if (selected) shapeRenderer.polygon(polygon.getTransformedVertices());
//	    shapeRenderer.x(sprite.getOriginX(),sprite.getOriginY(),10);
//	    shapeRenderer.x(getX(),getY(),BaseLength/10);
	    shapeRenderer.end();
	}
	
	public boolean contains(float x, float y) {
		return polygon.contains(x, y);
	}
	
	public void setPosition(float x, float y) {
	    sprite.setPosition(x,y);
	    polygon.setPosition(x,y);
		}
	
	public void setScale(float s) {
	    sprite.setScale(s);
	    polygon.setScale(s,s);
		}

	public float getX() {
		return polygon.getX();
	}
	public float getY() {
		return polygon.getY();
	}
	
	public void setRotation(float degrees) {
	    sprite.setRotation(degrees);
	    polygon.setRotation(degrees);
		}

	public float getRotation() {
		return polygon.getRotation();
	}
    
}
