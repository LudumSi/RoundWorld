package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class BaseActor extends Group {
	public TextureRegion region;
	public Rectangle boundary;
	public Vector2 vel;
	protected Polygon boundingPolygon;
	public float centerX;
	public float centerY;
	public Vector2 center;
	
	

	protected float elapsedTime;
	private String path;
	
	public Texture texture;
	public Animation<TextureRegion> animation;
	
	/**
	 * creates a BaseActor with the texture at path
	 * @param path the path to the texture
	 */
	@Deprecated
	public BaseActor(String path) {
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		vel = new Vector2(0, 0);
		elapsedTime = 0;
		this.center = new Vector2();
		this.path = path;
		genTexture(this.path);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);
		
		this.setPolygonBoundary(
				0, 0, 
				this.getWidth(), 0, 
				this.getWidth(), this.getHeight(), 
				0, this.getHeight()
			);
		
	}

	/**
	 * creates a BaseAcotr with a texture
	 * @param t the texture
	 */
	public BaseActor(Texture t) {
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		vel = new Vector2(0, 0);
		elapsedTime = 0;
		this.center = new Vector2();
		this.setTexture(t);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);

		this.setPolygonBoundary(
				0, 0, 
				this.getWidth(), 0, 
				this.getWidth(), this.getHeight(), 
				0, this.getHeight()
			);
	


	}
	/**
	 * creates a BaseActor with no texture.
	 * Rendering this will cause a null pointer exception
	 */
	public BaseActor(){
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		vel = new Vector2(0, 0);
		elapsedTime = 0;
		this.center = new Vector2();
	
		
		this.setPolygonBoundary();

	}

	/**
	 * sets this BaseActor's texture to the given texture. Updates width, height, and origin
	 * @param t the texture
	 */
	public void setTexture(Texture t) {
		texture = t;
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth(w);
		setHeight(h);
		region.setRegion(t);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);

		

	}
	/**
	 * generates a texture from a path, substitutes a missing texture if not found
	 * @param path the path to the texture
	 */
	@Deprecated
	public void genTexture(String path) {
	
		try {
			Texture t = new Texture(Gdx.files.internal(path));
			setTexture(t);
			

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");
			Texture t = new Texture(Gdx.files.internal("assets/missingTex.png"));
			setTexture(t);
		}
	}

	/**
	 * Creates an animation from a texture sheet
	 * 
	 * @param sheet
	 * @param cols
	 * @param rows
	 * @param speed
	 */
	@Deprecated
	public void genAnimation(Texture sheet, int cols, int rows, float speed) {
		
		
		TextureRegion[][] map = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		TextureRegion[] frames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = map[i][j];
			}
		}
		animation = new Animation<TextureRegion>(speed, frames);
		
		int w = frames[0].getRegionWidth();
		int h = frames[0].getRegionHeight();
		setWidth(w);
		setHeight(h);
	
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);
	
	
		
		
		
		
	}
	
	/**
	 * sets the BaseActor's animation. If a BaseActor's animation is not
	 * equal to null, then that animation will run
	 * @param a the animation
	 */
	public void setAnimation(Animation<TextureRegion> a){
		this.animation = a;
	
	
	}

	/**
	 * 
	 * @return this BaseActor's rectangle boundary
	 */
	public Rectangle getBoundingRectangle() {
		boundary.set(getX(), getY(), getWidth(), getHeight());
		return boundary;

	}
	
	/**
	 * sets this BaseActor's boundingPolygon with points in verts;
	 */
	public void setPolygonBoundary(float...verts) {
		float w = getWidth();
		float h = getHeight();
		if (verts.length % 2 != 0) {
			
			System.out.println("Warning: Invalid polygon verts on BaseActor, defaulting");
			float v[] = { 0, 0, w, 0, w, h, 0, h };
			boundingPolygon = new Polygon(v);
			boundingPolygon.setOrigin(getOriginX(), getOriginY());
			
		} else {
			
			boundingPolygon = new Polygon(verts);
			boundingPolygon.setOrigin(getOriginX(), getOriginY());
		}
		
		
	}

	/**
	 * 
	 * @return this BaseActor's boundingPolygon
	 */
	public Polygon getBoundingPolygon() {
		boundingPolygon.setPosition(getX(), getY());
		boundingPolygon.getRotation();
		return boundingPolygon;
	}

	/**
	 * Checks to see if this BaseActor overlaps another
	 * @param other the BaseActor to check against
	 * @param resolve whether or not to move this BaseActor out of the other
	 * @return whether or not this BaseActor overlaps other
	 */
	public boolean overlaps(BaseActor other, boolean resolve) {
		Polygon poly1 = this.getBoundingPolygon();
		Polygon poly2 = other.getBoundingPolygon();

		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return false;

		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polyOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

		if (polyOverlap && resolve) {
			this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		}
		float significant = 0.5f;
		return (polyOverlap && (mtv.depth > significant));

	}

	public void act(float dt) {
		super.act(dt);
		moveBy(vel.x * dt, vel.y * dt);
		elapsedTime += Gdx.graphics.getDeltaTime();
		center.x = this.getX() + this.getWidth() / 2;
		center.y = this.getY() + this.getHeight() / 2;
		
	
	//	this.setOriginX(this.getWidth() / 2);
	//	this.setOriginY(this.getHeight() / 2);

	}
	
	/**
	 * draws this BaseActor
	 */
	public void draw(Batch batch, float parentAlpha) {
		// region.setRegion(anim.getKeyFrame(elapsedTime));

		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if (isVisible()) {
			if (this.animation != null){
				
				batch.draw(this.animation.getKeyFrame(elapsedTime, true),getX(),getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
			
			} else {
				batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

			}
		
		}
		super.draw(batch, parentAlpha);
	}
	
	/**
	 * Sets this BaseActor to copy another BaseActor, specifically
	 * texture, position, color, origin, and visibility
	 * @param orig the BaseActor to copy from
	 */
	public void copy(BaseActor orig) {
		this.region = new TextureRegion(orig.region);
		if (orig.boundingPolygon != null) {
			this.boundingPolygon = new Polygon(orig.boundingPolygon.getVertices());
			this.boundingPolygon.setOrigin(orig.getOriginX(), orig.getOriginY());

		}
		this.setPosition(orig.getX(), orig.getY());
		this.setOriginX(orig.getOriginX());
		this.setOriginY(orig.getOriginY());
		this.setWidth(orig.getWidth());
		this.setHeight(orig.getHeight());
		this.setColor(orig.getColor());
		this.setVisible(orig.isVisible());

	}

	/**
	 * Clones this BaseActor
	 * @return a shallow copy of this BaseActor
	 */
	public BaseActor clone() {
		System.out.println("clone!");
		BaseActor newbie = new BaseActor(this.texture);
		newbie.copy(this);
		return newbie;
	}
	
	/**
	 * Points the BaseActor toward a given point
	 * @param x the x cord of the point
	 * @param y the y cord of the point
	 * @param speed the speed to rotate at
	 * @param rotate whether or not to rotate the BaseActor to point at the point
	 * @return the angle between the BaseActor and the point
	 */
	public double pointAt(double x, double y, float speed, boolean rotate) {

		double yDiff = y - this.center.y;
		double xDiff = x - this.center.x;
		double newAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));

		if (rotate) {
			this.addAction(Actions.rotateTo((float) newAngle, speed));
		}
		return newAngle;

	}

	/**
	 * Calculates the distance from the center of this BaseActor to an
	 * arbitrary point
	 * @param x the x cord of the point
	 * @param y the y cord of the point
	 * @return the distance to said point
	 */
	public double distanceTo(double x, double y) {

		return Math.hypot(Math.abs(this.center.x - x), Math.abs(this.center.y - y));
	}
	
	/**
	 * Sets the center of this BaseActor
	 * @param x the x cord
	 * @param y the y cord
	 */
	public void setCenter(float x, float y) {
		
		this.setPosition(x - this.getWidth() / 2, y - this.getHeight() / 2);
	}
	
	/**
	 * Slices the baseActor into a smaller texture, updates height and width
	 * @param u the x cord to slice from
	 * @param v the y cord to slice from
	 * @param u2 the width of the slice
	 * @param v2 the height of the slice
	 */
	public void slice(int u, int v, int u2, int v2) {
		this.region.setRegion( u, v, u2, v2);
		this.setWidth(u2 - u);
		this.setHeight(v2 - v);
	}
	
	/**
	 * Reverts any splicing on this BaseActor
	 */
	public void revert() {
		this.setTexture(this.texture);
	}
	
	

}
