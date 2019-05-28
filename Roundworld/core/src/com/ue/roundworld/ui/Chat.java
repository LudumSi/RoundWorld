package com.ue.roundworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.AssetManager;
import com.ue.roundworld.BaseActor;
import com.ue.roundworld.GameplayScreen;
import com.ue.roundworld.RoundWorld;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class Chat extends BaseActor{
	
	public TextInput textInput;
	Label[] log = new Label[7];
	String lastText = "";
	String lastAdd = "";
	
	public Chat() {
		super(AssetManager.getTexture("ui_chat"));
		
		for (int i = 0; i < log.length; i++) {
			Label l = new Label("re", RoundWorld.font);
			l.setPosition(3, (7 * 16) - i * 16);
			this.addActor(l);
			log[i] = l;
		}
		textInput = new TextInput(3, 8);
		this.addActor(textInput);
	}
	
	/**
	 * sets the input processor to this text input
	 */
	public void select() {
		Gdx.input.setInputProcessor(textInput);
	}
	
	public void act(float dt) {
		super.act(dt);
		if (Client.isConnected()) {
			Event e = Client.getParsedData();
			
			if(Event.verify(e, "chat_message")) {
				Client.popParsedData();
				
				
				add_to_log(e.getString("name"), e.getString("text"), Color.valueOf(e.getString("color")));
						
					
			}
		}
		if (!lastText.equals(textInput.getInput())) {
			add_to_log(Client.user, textInput.getInput(), Color.WHITE);
			Event e = new Event("chat_message");
			e.addArg("name", Client.user);
			e.addArg("text", "\""+ textInput.getInput() + "\"");
			e.addArg("color", Color.WHITE.toString());
			e.generate();
			if (Client.isConnected()) {
				Client.sendRequest(e.generate());
			}
			
			lastText = textInput.getInput();
			
		}
	}
	
	/**
	 * adds a message to the chat
	 * @param name the owner of the message
	 * @param text the message to put in the chat
	 * @param c the color of the message
	 */
	public void add_to_log(String name, String text, Color c) {
		for (int i = 0; i < log.length-1; i++) {
			log[i].setText(log[i+1].getText());
			log[i].setColor(log[i+1].getColor());
		}
		log[6].setText(name + ": " + text);
		log[6].setColor(c);
	}
	
	/**
	 * clears the chat
	 */
	public void clearLog() {
		for (Label l : log) {
			l.setText("");
		}
	}
	
}
