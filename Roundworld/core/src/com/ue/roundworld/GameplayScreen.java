package com.ue.roundworld;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
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
	
	private RenderManager renderManager;
	private Player player;
	private Entity ghost;
	private Entity ghost2;
	
	
	private UiBase uiBase;
	
	public GameplayScreen(Game g){
		game = g;
		renderManager = new RenderManager();
		create();
		
	}
	
	public void create() {
		
		mainStage = new Stage();
		uiStage = new Stage();
		
		//get renders
		//while(!renderManage.getRenders(mainStage));
		
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				BaseActor ba = new BaseActor(AssetManager.getTexture("grass_0" + Integer.toString(MathUtils.random(0, 3))));
				ba.sizeBy(16, 16);
				ba.setPosition(i * 32, j * 32);
				mainStage.addActor(ba);
			}
			
		}
		
		
		uiBase = new UiBase();
	
		
		
		text = new Label(" ", RoundWorld.font);
		text.setPosition(5, 5);
		mainStage.addActor(text);
		
		

		player = new Player();
		player.setPosition(50, 50);
		
		ghost = new Entity(Utils.missingTexture);
		ghost.setPosition(100, 50);
		
		ghost2 = new Entity(Utils.missingTexture);
		ghost2.setPosition(150, 50);
		
		mainStage.addActor(player);
		mainStage.addActor(ghost);
		mainStage.addActor(ghost2);
	
		uiStage.addActor(uiBase);
		Gdx.input.setInputProcessor(InputProcess.instance);
	}
	
	public void render(float dt){
		
		
		mainStage.act();
		uiStage.act();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	
	
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
