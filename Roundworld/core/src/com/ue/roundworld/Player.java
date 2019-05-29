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

	public Player(String name, Color nameColor) {
		super(AssetManager.getTexture("character_01"));
		this.genAnimation(AssetManager.getTexture("character_idle"), 2, 2, 0.5f * 0.3f);
		this.nameColor = nameColor;
		this.name = name;
		nameDisp.setText(name);
		nameDisp.setPosition(this.center.x, this.getHeight() + 10);
		nameDisp.setColor(nameColor);
		this.addActor(nameDisp);
	}

	public void act(float dt) {
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
	/**
	 * sends an event to the server to spawn in a player with name name
	 * BLOCKING 
	 * @param name the name of the player to spawn in
	 * @return the player with name name
	 */
	public static Player spawn(String name, boolean isClient) {
		Event e = new Event("spawn_player");
		e.addArg("name", name);
		if (isClient) {
			e.addArg("isClient", 1);
		}
		
		Player p = null;
		while (p == null) {
			Event ev = Client.popParsedData();
			if (Event.verify(ev, "re:spawn_player")) {
				p = new Player(ev.getString("name"), Color.valueOf(ev.getString("color")));
				if (ev.getInt("isClient") == 1) {
					p.isClient = true;
				}
			}
		}
		
		return p;
		
	}
}
