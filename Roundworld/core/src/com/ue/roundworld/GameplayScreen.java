package com.ue.roundworld;

import java.io.File;
import java.math.RoundingMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;
import com.ue.roundworld.client.Parser;
import com.ue.roundworld.client.RenderManager;
import com.ue.roundworld.ui.TextInput;
import com.ue.roundworld.ui.UiBase;

public class GameplayScreen implements Screen {
	
	public Stage mainStage;
	public Stage uiStage;
	public Game game;
		
	public BaseActor test;
	public Label text;
	
	
	private TextInput textInput;
	
	private RenderManager renderManager;
	private Player player;
	private Entity ghost;
	private Entity ghost2;
	
	private UiBase uiBase;
	
	private OrthographicCamera camera;
	private Vector2 cam_d = new Vector2();
	private float deadzone_scale = (float) .075;
	private float deadzone_x_val;
	private float deadzone_y_val;
	
	public GameplayScreen(Game g){
		game = g;
		renderManager = new RenderManager();
		create();
	}
	
	public void create() {
		mainStage = new Stage();
		uiStage = new Stage();
		
		deadzone_x_val = (float) (deadzone_scale * RoundWorld.unscaledWidth);
		deadzone_y_val = (float) (deadzone_scale * RoundWorld.unscaledHeight);
		
		camera = (OrthographicCamera) mainStage.getCamera();
		
		//get renders
		//while(!renderManage.getRenders(mainStage));
		
		for (int i = -25; i < 50; i++) {
			for (int j = -25; j < 50; j++) {
				BaseActor ba = new BaseActor(AssetManager.getTexture("grass_0" + Integer.toString(MathUtils.random(0, 3))));
				ba.sizeBy(16);
				ba.setPosition(32 * i, 32 * j);
				mainStage.addActor(ba);
			}
		}
		
		
		uiBase = new UiBase();
	
		
		
		text = new Label(" ", RoundWorld.font);
		text.setPosition(5, 5);
		mainStage.addActor(text);
		
		

		player = new Player();
		player.setPosition((RoundWorld.unscaledWidth - player.getWidth()) / 2, (RoundWorld.unscaledHeight - player.getHeight()) / 2);
		camera.position.set(RoundWorld.unscaledWidth / 2, RoundWorld.unscaledHeight / 2, camera.position.z);
		
		ghost = new Entity(Utils.missingTexture);
		ghost.setPosition(100, 50);
		
		ghost2 = new Entity(Utils.missingTexture);
		ghost2.setPosition(150, 50);
		
		
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		
	
		
		
		mainStage.addActor(player);
		mainStage.addActor(ghost);
		mainStage.addActor(ghost2);
		
		//AssetManager.load_textures(new File("assets/"));
		
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
		
		move_camera(dt);
		mainStage.getViewport().apply();
		mainStage.draw();
		uiStage.getViewport().apply(true);
		uiStage.draw();
		
		
	
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (Client.isConnected()) {
				Client.close();
			}
			
			Gdx.app.exit();
		}
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			if(RoundWorld.scale == 1)
			{
				RoundWorld.scale = 2;
			}
			else
			{
				RoundWorld.scale = 1;
			}
			
			((RoundWorld) game).enforce_scaling();
			Gdx.graphics.setWindowedMode(RoundWorld.width, RoundWorld.height);
		}
	}
	
	/*
	 *	description:	Changes camera position based on player movement.
	 *	pre-condition:	Called only once per render() call
	 */
	void move_camera(float dt)
	{
		cam_d.x = player.getX() + player.getWidth() / 2 - camera.position.x;
		cam_d.y = player.getY() + player.getHeight() / 2 - camera.position.y;
		
		/* deadzone check */
		cam_d.scl((cam_d.x < deadzone_x_val) ? (.75f) : (1), (cam_d.y < deadzone_y_val) ? (.75f) : (1));
		
		/* account for dt */
		cam_d = cam_d.scl(1.5f * dt);
		
		/* move camera */
		camera.translate(cam_d.x, cam_d.y);
	}
	
	/*
	 * Updates viewport fields to account for new window / scaled dimensions
	 */
	void update_viewport()
	{
		deadzone_x_val = (float) (deadzone_scale * RoundWorld.unscaledWidth);
		deadzone_y_val = (float) (deadzone_scale * RoundWorld.unscaledHeight);
		mainStage.getViewport().setScreenSize(RoundWorld.width, RoundWorld.height);
		mainStage.getViewport().setWorldSize(RoundWorld.unscaledWidth, RoundWorld.unscaledHeight);
		uiStage.getViewport().setScreenSize(RoundWorld.width, RoundWorld.height);
		uiStage.getViewport().setWorldSize(RoundWorld.unscaledWidth, RoundWorld.unscaledHeight);
		uiBase.resetElementPositions();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		((RoundWorld) game).adaptScalingToWindow();
		update_viewport();
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
