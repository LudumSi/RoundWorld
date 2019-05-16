package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Entity{

	
	public Player() {
		super(AssetManager.getTexture("character_01"));
		this.genAnimation(AssetManager.getTexture("character_idle"), 2, 2, 0.5f * 0.3f); 
	}
	
	public void act(float dt) {
		
		if (Gdx.input.getInputProcessor() == InputProcess.instance) {
			this.setVelX(0);
			this.setVelY(0);
			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.setVelY(5);
				this.setVelX(0);
				
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				this.setVelX(5);
				this.setVelY(0);
				
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				this.setVelY(-5);
				this.setVelX(0);
				
			} else if (Gdx.input.isKeyPressed(Keys.A)) {
				this.setVelX(-5);
				this.setVelY(0);
				
			}
		}
		super.act(dt);
		
	}
}
