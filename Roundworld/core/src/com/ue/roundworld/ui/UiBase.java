package com.ue.roundworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ue.roundworld.AssetManager;
import com.ue.roundworld.BaseActor;
import com.ue.roundworld.InputProcess;
import com.ue.roundworld.RoundWorld;
import com.ue.roundworld.Utils;
import com.ue.roundworld.client.Client;

public class UiBase extends BaseActor{
	
	private Bar phb;
	private Bar pmb;
	
	private Chat chat;

	private Vector2 mousePos = new Vector2();
	
	public UiBase() {
		super(Utils.emptyTexture);
		
	
		
		//health bars
		phb = new Bar(AssetManager.get_texture("health_bar_back"), AssetManager.get_texture("health_bar"));
		pmb = new Bar(AssetManager.get_texture("mana_bar_back"), AssetManager.get_texture("mana_bar"));
		phb.setPosition(0, RoundWorld.height - 16);
		pmb.setPosition(0, RoundWorld.height - 16 - 8);
		this.addActor(phb);
		this.addActor(pmb);
		pmb.sub(1);
		
		//chat
		chat = new Chat();
		chat.setPosition(5, 5);
		this.addActor(chat);
		
		Gdx.input.setInputProcessor(chat.textInput);
		chat.addAction(Actions.fadeOut(0));
	}
	
	public void act(float dt) {
		super.act(dt);
		
		mousePos = this.screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
	
		phb.sub(0.001f);
		pmb.add(0.001f);
		chatUpdate();
		
	}
	
	private void chatUpdate() {
		if (Gdx.input.isKeyJustPressed(Keys.T)) {
			if (Gdx.input.getInputProcessor() == InputProcess.instance) {
				chat.addAction(Actions.fadeIn(1));
				Gdx.input.setInputProcessor(chat.textInput);
			}
		}
		if (!chat.getBoundingRectangle().contains(mousePos) && Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.input.setInputProcessor(InputProcess.instance);
			chat.addAction(Actions.fadeOut(1));
			chat.textInput.erase();
			
			
		}
		
		
	}
}
