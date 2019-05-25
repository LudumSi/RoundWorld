package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Entity {

	int dx;
	int dy;

	public Player() {
		super(AssetManager.getTexture("character_01"));
		this.genAnimation(AssetManager.getTexture("character_idle"), 2, 2, 0.5f * 0.3f);
	}

	public void act(float dt) {
		if (Gdx.input.getInputProcessor() == InputProcess.instance) {
			dy = 0;
			dx = 0;

			/* handle inputs */
			if (Gdx.input.isKeyPressed(Keys.W)) {
				dy += 3;
			}
			if (Gdx.input.isKeyPressed(Keys.D)) {
				dx += 3;
			}
			if (Gdx.input.isKeyPressed(Keys.S)) {
				dy -= 3;
			}
			if (Gdx.input.isKeyPressed(Keys.A)) {
				dx -= 3;
			}

			/* push current velocities */
			this.setVelX(dx);
			this.setVelY(dy);
		}
		
		super.act(dt);
	}
}
