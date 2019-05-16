package com.ue.roundworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Projectile extends BaseActor{
	
	
	
	
	
	private float accel;
	public float angle;
	private float angleVel;
	private int lifetime = 0;
	private float angleMultX;
	private float angleMultY;
	public float vel;

	public boolean isInteractable = true;
	public String id = "";
	private boolean kill = false;

	public float orbitSpeed;
	public float orbitPos;
	public float orbitDist;

	public int misc;
	

	public static ArrayList<Projectile> projs = new ArrayList<Projectile>();
	
	/**
	 * use spawnBullet instead
	 */
	public Projectile() {
		super();
		
		
	}
	/**
	 * spawns a Projectile
	 * @param s the stage to add the projectile to 
	 * @param pos the position to spawn the projectile at
	 * @param vel the velocity to give the projectile
	 * @param accel the acceleration to give the projectile
	 * @param angle the angle that the projectile will move in
	 * @param angleVel the angular velocity of the projectile
	 * @param type the texture of the projectile
	 * @param c the color of the projectile
	 * @param id the id of the projectile
	 * @return the projectile spawned
	 */
	public static Projectile spawnBullet(Stage s, Vector2 pos, float vel, float accel, float angle, float angleVel, Texture type, Color c, String id){
		
		
		
		
		Projectile b = new Projectile();
		b.setTexture(type);
		b.id = id;
		b.setColor(c);
	
		
		b.setCenter(pos.x, pos.y);
		b.vel = vel;
	
		b.accel = accel;
		
		b.angle = angle;
		b.angleVel = angleVel;
		
		b.lifetime = 0;
	
		b.setRotation(angle);
		b.kill = false;
		
		
		if (s != null && b != null){
			s.addActor(b);
			b.addAction(Actions.fadeOut(0));
			b.addAction(Actions.fadeIn(0.3f));
			
		}
		projs.add(b);
		return b;
	
	}
	
	@Override
	public void act(float dt){

		super.act(dt);
		angle += angleVel;
		angleMultX = Utils.convertVel(angle).x;
		angleMultY = Utils.convertVel(angle).y;
		vel += accel;
		this.setRotation(angle);
		
		
		this.moveBy(vel * angleMultX * dt * 60, vel * angleMultY * dt * 60);
	
		lifetime += 1;
		
		
	
		
		
	}
	
	
	/**
	 * deletes this projectile
	 */
	public void kill(){
		this.setCenter(-1000, -1000);
		this.kill = true;
		
	
	}
	public int getLifetime(){
		return this.lifetime;
	}
	
	
	
	/**
	 * causes this projectile to orbit a point
	 * @param x the x cord to orbit
	 * @param y the y cord to orbit
	 * @param angle the angle to begin orbiting at
	 * @param speed the speed at which to orbit
	 * @param radius the radius at which to orbit at
	 */
	public void orbit(float x, float y, float angle, float speed, float radius) {
		this.setCenter(x + (float)Math.cos(angle + orbitSpeed) * radius, y + (float)Math.sin(angle + orbitSpeed) * radius);
		orbitSpeed += speed;
		orbitDist = radius;
	}

	
	
		
}
