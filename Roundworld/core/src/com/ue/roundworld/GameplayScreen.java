package com.ue.roundworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;
import com.ue.roundworld.client.Parser;
import com.ue.roundworld.client.RenderManager;

public class GameplayScreen implements Screen{
	
	public Stage mainStage;
	public Stage uiStage;
	public Game game;
		
	public BaseActor test;
	public Label text;
	public static Client client;
	
	private TextInput textInput;
	
	private RenderManager renderManager;
	private Player player;
	
	public GameplayScreen(Game g, Client c){
		game = g;
		client = c;
		renderManager = new RenderManager(c);
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
		
		
		test.scaleBy(4, 4);
		player = new Player();
		player.setPosition(50, 50);
		
		
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		
		textInput = new TextInput(0,0, client);
		mainStage.addActor(textInput);
		Gdx.input.setInputProcessor(textInput);
		
		mainStage.addActor(player);
		
	}
	
	public void render(float dt){
		
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		test.rotateBy(1);
		test.addAction(Actions.sequence(
				Actions.scaleBy(1.5f, 1.5f, 0.1f),
				Actions.scaleBy(0.5f, 0.5f, 0.1f)
				));
	
		//System.out.println(client.getRecievedData());
		
		textInput.update();
		
		renderManager.render(player);
		
		mainStage.draw();
		uiStage.draw();
		
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			
			
			Gdx.app.exit();
		}
		
		if (!client.getConnect()) {
			textInput.add_to_log("SERVER:", "Connection Failed", Color.RED);
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
