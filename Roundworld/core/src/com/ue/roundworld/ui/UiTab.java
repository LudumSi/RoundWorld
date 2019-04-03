package com.ue.roundworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ue.roundworld.BaseActor;
import com.ue.roundworld.InputProcess;

public abstract class UiTab extends BaseActor{
	
	
	private int openKey;
	private InputProcessor inputProcess;
	
	public UiTab(Texture t, int openKey, InputProcessor inputProcess) {
		super(t);
		this.openKey = openKey;
		this.inputProcess = inputProcess;
	}
	
	public void update(Vector2 mousePos) {
		if (Gdx.input.isKeyJustPressed(openKey)) {
			if (Gdx.input.getInputProcessor() == InputProcess.instance) {
				this.addAction(Actions.fadeIn(1));
				Gdx.input.setInputProcessor(this.inputProcess);
			}
		}
		if (!this.getBoundingRectangle().contains(mousePos) && Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.input.setInputProcessor(InputProcess.instance);
			this.addAction(Actions.fadeOut(1));
			
			
			
		}
	}
	
	
}
