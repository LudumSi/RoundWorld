package com.ue.roundworld;

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

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;
import com.ue.roundworld.ui.UiBase;

public class GameplayScreen implements Screen {
	public Stage mainStage;
	public Stage uiStage;
	
	public Game game;
	public MenuScreen menu;
	public SettingsScreen settings;
	
	public BaseActor test;
	public Label text;
	
	private Player player;
	private Entity ghost;
	private Entity ghost2;
	
	private UiBase uiBase;
	
	private OrthographicCamera camera;
	private Vector2 cam_d = new Vector2();
	private float currentZoom = 1f;
	
	private float deadzoneScale = (float) .005;
	private float deadzoneXVal;
	private float deadzoneYVal;
	
	public GameplayScreen(Game g, MenuScreen menu, SettingsScreen settings){
		game = g;
		this.menu = menu;
		this.settings = settings;
		/* create called before being opened */
	}
	
	public void create() {
		mainStage = new Stage();
		uiStage = new Stage();
		
		deadzoneXVal = (float) (deadzoneScale * RoundWorld.unscaledWidth / 2 / currentZoom);
		deadzoneYVal = (float) (deadzoneScale * RoundWorld.unscaledHeight / 2 / currentZoom);
		
		camera = (OrthographicCamera) mainStage.getCamera();
				
		//RenderManager.getRenders("test", mainStage);
		

		
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
		Event e = Client.getParsedData();
		if (Event.verify(e, "client_connect")) {
			Client.popParsedData();
		}
		
		moveCamera(dt);
		
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			if(RoundWorld.targetZoom > RoundWorld.minZoom)
			{
				RoundWorld.targetZoom *= .99;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.E)) {
			if(RoundWorld.targetZoom < RoundWorld.maxZoom)
			{
				RoundWorld.targetZoom *= 1.01;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(settings);
		}
		RenderManager.render(player);
		mainStage.getViewport().apply();
		mainStage.draw();
		
		uiStage.getViewport().apply(true);
		uiStage.draw();
	}

	
	/*
	 *	description:	Changes camera position based on player movement.
	 *	pre-condition:	Called only once per render() call
	 */
	private void moveCamera(float dt)
	{
		if(player.getX() > camera.position.x)
		{
			cam_d.x = (Math.abs(player.getX() + player.getWidth() / 2 - camera.position.x) - deadzoneXVal);//player.getX() + player.getWidth() / 2 - camera.position.x;
		}
		else
		{
			
		}
	
		cam_d.x = ((player.getX() > camera.position.x) ? (1f) : (-1f)) * (Math.abs(player.getX() + player.getWidth() / 2 - camera.position.x) - deadzoneXVal);
		cam_d.y = ((player.getY() > camera.position.y) ? (1f) : (-1f)) * (Math.abs(player.getY() + player.getHeight() / 2 - camera.position.y) - deadzoneYVal);
		
		/* deadzone check */
		cam_d.scl((Math.abs(cam_d.x) < deadzoneXVal && RoundWorld.smoothCam) ? (0f) : (1f), (Math.abs(cam_d.y) < deadzoneYVal && RoundWorld.smoothCam) ? (0f) : (1f));
		
		/* account for dt */
		cam_d = cam_d.scl((RoundWorld.smoothCam) ? (1.5f * dt) : (1f));
		
		/* move camera */
		camera.translate(cam_d.x, cam_d.y);
		
		/* update currentZoom */
		currentZoom += ((RoundWorld.smoothZoom) ? (5 * dt) : (1)) * (RoundWorld.targetZoom - currentZoom);
		
		/* update viewports */
		updateZoomViewportStuff();
	}
	
	
	/*
	 * Updates viewport fields to account for new window / scaled dimensions
	 */
	private void updateViewports()
	{
		deadzoneXVal = (float) (deadzoneScale * RoundWorld.unscaledWidth / currentZoom);
		deadzoneYVal = (float) (deadzoneScale * RoundWorld.unscaledHeight / currentZoom);
		mainStage.getViewport().setScreenSize(RoundWorld.width, RoundWorld.height);
		mainStage.getViewport().setWorldSize(RoundWorld.unscaledWidth / currentZoom, RoundWorld.unscaledHeight / currentZoom);
		uiStage.getViewport().setScreenSize(RoundWorld.width, RoundWorld.height);
		uiStage.getViewport().setWorldSize(RoundWorld.unscaledWidth, RoundWorld.unscaledHeight);
		uiBase.resetElementPositions();
	}
	
	private void updateZoomViewportStuff()
	{
		deadzoneXVal = (float) (deadzoneScale * RoundWorld.width / currentZoom);
		deadzoneYVal = (float) (deadzoneScale * RoundWorld.height / currentZoom);
		mainStage.getViewport().setWorldSize(RoundWorld.unscaledWidth / currentZoom, RoundWorld.unscaledHeight / currentZoom);
		uiBase.resetElementPositions();
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		if(RoundWorld.autoScaling)
		{
			((RoundWorld) game).autoScale();
		}
		((RoundWorld) game).adaptScalingToWindow();
		uiBase.resetElementPositions();
		updateViewports();
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
