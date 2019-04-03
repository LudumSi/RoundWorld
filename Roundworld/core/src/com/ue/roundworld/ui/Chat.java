package com.ue.roundworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.AssetManager;
import com.ue.roundworld.BaseActor;
import com.ue.roundworld.RoundWorld;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;

public class Chat extends BaseActor{
	
	public TextInput textInput;
	Label[] log = new Label[7];
	Client c;
	String lastText = "";
	String lastAdd = "";
	
	public Chat() {
		super(AssetManager.get_texture("ui_chat"));
		
		for (int i = 0; i < log.length; i++) {
			Label l = new Label("re", RoundWorld.font);
			l.setPosition(3, (7 * 16) - i * 16);
			this.addActor(l);
			log[i] = l;
		}
		textInput = new TextInput(3, 8);
		this.addActor(textInput);
	}
	
	public void select() {
		Gdx.input.setInputProcessor(textInput);
	}
	
	public void act(float dt) {
		super.act(dt);
		if (c != null) {
			Command com = c.getParsedData();
			
			if(com!= null && com.get_id() == 0xC001 && com.get_component(0, 0) != null && com.get_component(0, 1) != null) {
				
					if (
						!lastText.equals(com.get_component(0, 1).getArg("text"))) {
						lastText = com.get_component(0, 1).getArg("text");
						add_to_log( com.get_component(0, 0).getArg("text"), com.get_component(0, 1).getArg("text"), Color.WHITE);
						
					}
			}
		}
		if (!lastText.equals(textInput.getInput())) {
			add_to_log(Client.user, textInput.getInput(), Color.WHITE);
			lastText = textInput.getInput();
		}
	}
	
	public void add_to_log(String name, String text, Color c) {
		for (int i = 0; i < log.length-1; i++) {
			log[i].setText(log[i+1].getText());
			log[i].setColor(log[i+1].getColor());
		}
		log[6].setText(name + ": " + text);
		log[6].setColor(c);
	}
	
	public void clearLog() {
		for (Label l : log) {
			l.setText("");
		}
	}
	
}
