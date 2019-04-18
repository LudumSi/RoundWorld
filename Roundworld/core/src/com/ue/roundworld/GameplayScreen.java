package com.ue.roundworld;

import java.io.File;

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
import com.ue.roundworld.ui.TextInput;
import com.ue.roundworld.ui.UiBase;

public class GameplayScreen implements Screen{
	
	public Stage mainStage;
	public Stage uiStage;
	public Game game;
		
	public BaseActor test;
	public Label text;
	
	
	private TextInput textInput;
	
	private RenderManager renderManager;
	private Player player;
	private Entity ghost;
	
	private UiBase uiBase;
	
	public GameplayScreen(Game g){
		game = g;
		renderManager = new RenderManager();
		create();
		
	}
	
	public void create() {
		
		mainStage = new Stage();
		uiStage = new Stage();
		
		uiBase = new UiBase();
		test = new BaseActor(AssetManager.get_texture("RW_Icon_64"));
		test.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		mainStage.addActor(test);
		
		text = new Label(" ", RoundWorld.font);
		text.setPosition(5, 5);
		mainStage.addActor(text);
		
		
		test.scaleBy(4, 4);
		player = new Player();
		player.setPosition(50, 50);
		
		ghost = new Entity(Utils.missingTexture);
		ghost.setPosition(100, 50);
		
		
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		
	
		
		
		mainStage.addActor(player);
		mainStage.addActor(ghost);
		
		//AssetManager.load_textures(new File("assets/"));
		
		uiStage.addActor(uiBase);
		Gdx.input.setInputProcessor(InputProcess.instance);
	}
	
	public void render(float dt){
		

		mainStage.act();
		uiStage.act();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		test.rotateBy(1);
	
	
		//System.out.println(client.getRecievedData());
		
		
		
		renderManager.render(player);
		
		mainStage.draw();
		uiStage.draw();
		
	
		
		
		
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
