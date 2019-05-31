package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class Player extends Entity {

	int dx;
	int dy;
	
	private Label nameDisp = new Label("", RoundWorld.font);
	private Color nameColor = new Color(1f, 0.5f, 0.5f, 1f);
	private String name;
	private boolean isClient;
	
	private String area;

	public Player() {
		super(AssetManager.getTexture("character_01"));
		this.genAnimation(AssetManager.getTexture("character_idle"), 2, 2, 0.5f * 0.3f);

		
		nameDisp.setPosition(this.center.x, this.getHeight() + 10);
		
		this.addActor(nameDisp);
	}

	public void act(float dt) {
		nameDisp.setText(name);
		nameDisp.setColor(nameColor);
		if (Gdx.input.getInputProcessor() == InputProcess.instance && isClient) {
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
	
	public void moveArea(String newArea) {
		this.area = newArea;
	
	}
	
	public void setName(String name) {
		this.name = name;
		super.setName(name);
	}
	
	public void setIsClient(boolean t) {
		this.isClient = t;
	}
	
	public void setNameColor(Color c) {
		this.nameColor = c;
	}
	
	public boolean isClient() {
		return this.isClient;
	}
}
