package com.ue.roundworld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Entity extends BaseActor{

	
	private Vector2 vel;

	
	public Entity(Texture t) {
		super(t);
	}
	
	public void addVel(int amtX, int amtY) {
		this.vel.x += amtX;
		this.vel.y += amtY;
	}
	public void setVelX(int amtX) {
		this.vel.x = amtX;
	
	}
	
	public void setVelY(int amtY) {
		this.vel.x = amtY;
	
	}
	public void act(float dt) {
		this.moveBy(dt * 60 * vel.x, dt * 60 * vel.y);
	}
}
