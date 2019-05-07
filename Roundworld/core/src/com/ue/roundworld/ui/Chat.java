package com.ue.roundworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.AssetManager;
import com.ue.roundworld.BaseActor;
import com.ue.roundworld.GameplayScreen;
import com.ue.roundworld.RoundWorld;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;

public class Chat extends BaseActor{
	
	public TextInput textInput;
	Label[] log = new Label[7];
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
		if (Client.isConnected()) {
			Command com = Client.popParsedData();
			
			if(com!= null && com.get_id() == 0xC001 && com.get_component(Component.Type.text, 0) != null && com.get_component(Component.Type.text, 1) != null) {
				
					
						
					add_to_log( com.get_component(Component.Type.text, 0).getArg(0), com.get_component(Component.Type.text, 1).getArg(0), Color.WHITE);
						
					
			}
		}
		if (!lastText.equals(textInput.getInput())) {
			add_to_log(Client.user, textInput.getInput(), Color.WHITE);
			if (Client.isConnected()) {
				Client.sendRequest(Command.generate(Command.Type.sendText,
						Component.generate(Component.Type.text, Client.user),
						Component.generate(Component.Type.text, textInput.getInput())
						));
			}
			
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
