package com.ue.roundworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;
import com.ue.roundworld.client.Parser;
import com.ue.roundworld.ui.TextInput;

public class CharacterCreationScreen implements Screen {
	
	public Stage mainStage;

	
	public Game game;
	
	private TextInput nameInput = new TextInput(10, 100);
	private TextInput colorInput = new TextInput(10, 50);
	
	private BaseActor doneButton = new BaseActor(AssetManager.getTexture("grass_00"));
	private GameplayScreen gameplay;
	
	public CharacterCreationScreen(Game g, GameplayScreen gameplay){
		game = g;
		this.gameplay = gameplay;
		create();
		
	}
	
	public void create() {
		
		nameInput.setTexture(AssetManager.getTexture("health_bar_back"));
		colorInput.setTexture(AssetManager.getTexture("health_bar_back"));

		mainStage = new Stage();
	
	
	}
	
	public void render(float dt){
		
		
	
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.act();
	
		

		if (nameInput.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY())) {
			Gdx.input.setInputProcessor(nameInput);
		}
		if (colorInput.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY())) {
			Gdx.input.setInputProcessor(nameInput);
		}
		if (doneButton.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY())) {
			Event e = new Event("new_player");
			
			JsonValue nameData = new JsonValue(ValueType.object);
			nameData.addChild("name", new JsonValue(nameInput.getInput()));
			nameData.addChild("name_color", new JsonValue(colorInput.getInput()));
			e.addArg("name_data", nameData);
			
			e.addArg("texture", "player");
			e.addArg("x", 0);
			e.addArg("y", 0);
			
			e.addArg("str", 10);
			e.addArg("dex", 10);
			e.addArg("int", 10);
			e.addArg("cha", 10);
			
			Client.sendRequest(e.generate());
			game.setScreen(gameplay);
		}
	
	
		
	
		
		
		mainStage.getViewport().apply();
		mainStage.draw();
		
		
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		if(RoundWorld.autoScaling)
		{
			((RoundWorld) game).autoScale();
		}
		((RoundWorld) game).adaptScalingToWindow();
		
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
