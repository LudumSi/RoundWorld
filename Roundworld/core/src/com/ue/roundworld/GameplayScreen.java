package com.ue.roundworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Parser;

public class GameplayScreen implements Screen{
	
	public Stage mainStage;
	public Stage uiStage;
	public Game game;
		
	public BaseActor test;
	public Label text;
	public Client client;
	
	private TextInput textInput;
	
	
	public GameplayScreen(Game g){
		game = g;
		create();
		
	}
	
	public void create() {
		
		mainStage = new Stage();
		uiStage = new Stage();
	
		test = new BaseActor("assets/RW_Icon_64.png");
		test.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		mainStage.addActor(test);
		
		text = new Label(" ", RoundWorld.font);
		text.setPosition(5, 5);
		mainStage.addActor(text);
		
		
		test.scaleBy(2, 2);
		
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		
		client = new Client("128.193.254.13", 1337);		
		textInput = new TextInput(0,0, client);
		mainStage.addActor(textInput);
		Gdx.input.setInputProcessor(textInput);
		
	}
	
	public void render(float dt){
		
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		test.rotateBy(1);
	
		//System.out.println(client.getRecievedData());
		
		textInput.update();
		
		mainStage.draw();
		uiStage.draw();
		
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			client.sendRequest("FFFF{(0:text|bye)}");
			
			client.close();
			Gdx.app.exit();
		}
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	


}
