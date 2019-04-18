package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Entity{

	
	public Player() {
		super(Utils.missingTexture);
	}
	
	public void act(float dt) {
		super.act(dt);
		if (Gdx.input.getInputProcessor() == InputProcess.instance) {
			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.setVelY(5);
				this.setVelX(0);
				this.sendVelUpdate();
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				this.setVelX(5);
				this.setVelY(0);
				this.sendVelUpdate();
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				this.setVelY(-5);
				this.setVelX(0);
				this.sendVelUpdate();
			} else if (Gdx.input.isKeyPressed(Keys.A)) {
				this.setVelX(-5);
				this.setVelY(0);
				this.sendVelUpdate();
			}
		}
		
	}
}
