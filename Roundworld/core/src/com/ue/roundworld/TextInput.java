package com.ue.roundworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;

public class TextInput extends BaseActor implements InputProcessor {
	
	
	String text;
	Label[] log = new Label[10];
	Client c;
	public TextInput(int x, int y, Client c) {
		super(Utils.emptyTexture);
		this.setPosition(x, y);
		this.c = c;
		
		for (int i = 0; i < 10; i++) {
			Label l = new Label("NONEW", RoundWorld.font);
			l.setPosition(0, x + 160 - i * 16);
			this.addActor(l);
			log[i] = l;
		}
	}
	
	public void add_to_log(String text) {
		for (int i = 0; i < log.length-1; i++) {
			log[i].setText(log[i+1].getText());
		}
		log[9].setText(text);
	}

	public void update() {
		Command com = c.getParsedData();
		System.out.println("P: "+ com.toString());
		if(com.get_id() == 1) {
			
			add_to_log(com.get_component(0).getArg("text"));
		}
	}
	
	public String getSpecialChar(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			return " ";
		}
		return "";
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode != Keys.ENTER) {
			text += Input.Keys.toString(keycode);
			log[9].setText(text);
		}
		else {
			add_to_log(text);
			c.sendRequest(Command.generate(
					Command.Type.sendText, 
					Component.generate(Component.Type.text, "text|" + text)
					));
			text = "";
			log[9].setText(text);
			
		}
		
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}