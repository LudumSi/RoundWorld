package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends BaseActor{

	
	public Player() {
		super(Utils.missingTexture);
	}
	
	public void act(float dt) {
		if (Gdx.input.isKeyJustPressed(Keys.W)) {
			this.moveBy(0, 5);
		} else if (Gdx.input.isKeyJustPressed(Keys.D)) {
			this.moveBy(5, 0);
		} else if (Gdx.input.isKeyJustPressed(Keys.S)) {
			this.moveBy(0, -5);
		} else if (Gdx.input.isKeyJustPressed(Keys.A)) {
			this.moveBy(-5, 0);
		}
	}
}
