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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;
import com.ue.roundworld.client.Parser;
import com.ue.roundworld.ui.TextInput;

public class MenuScreen implements Screen{
	
	public Stage mainStage;
	public Stage uiStage;
	
	public Game game;
	private SettingsScreen settings;
	private boolean settingsOpened;
	
	private BaseActor serverlessDebugButton = new BaseActor(AssetManager.getTexture("serverless_debug_button"));
	private BaseActor settingsButton = new BaseActor(AssetManager.getTexture("settings_button"));
	
	public BaseActor test;
	public BaseActor textBase = new BaseActor(Utils.emptyTexture);
	private TextInput usernameIn;
	private TextInput ipIn;
	
	private Label username;
	private Label serverIp;
	public Label text;
	
	public boolean retry;
	
	private float scaleVal = 1;
	
	private Color titleColor = Color.WHITE;
	
	private int connectAnimCountdown = -1;
	
	boolean connectDone = true;
	
	boolean titleUp = false;
	int titlePos = 0;
	
	private Music titleTheme;
	
	public MenuScreen(Game g){
		game = g;
		create();
	}
	
	public void create() {
		titleTheme = Gdx.audio.newMusic(Gdx.files.internal("assets/song_03.wav"));
		
		mainStage = new Stage();
		uiStage = new Stage();
		settingsOpened = false;
	
		test = new BaseActor(AssetManager.getTexture("RW_Icon_64"));
		
		text = new Label("", RoundWorld.font);
		textBase.addAction(Actions.fadeOut(0));
		
		username = new Label("Username: ", RoundWorld.font);
		username.setColor(Color.YELLOW);
		
		serverIp = new Label("Server Ip: ", RoundWorld.font);
		serverIp.setColor(Color.YELLOW);
		
		String ip = AssetManager.loadIp();
		if (ip != null) {
			Client.userIpAddress = ip;
			
		}
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		usernameIn = new TextInput(140, RoundWorld.unscaledHeight - 17);
		Gdx.input.setInputProcessor(usernameIn);
		
		ipIn = new TextInput(10, (int) (RoundWorld.unscaledHeight - 100));
	
		test.addAction(Actions.fadeOut(0));
		test.addAction(Actions.sequence(
				Actions.delay(6f),
				Actions.parallel(
						Actions.fadeIn(0.5f),
						Actions.rotateBy(360, 0.5f)
						
						)
				
				
				));
		
		/* start title animation */
		start_title_animation();
		
		/* place objects */
		resetPositions();
		resetSizing();
		addElementsToStage();
	}
	
	public void render(float dt){
		titleTheme.play();
		username.setText("Username:  " + usernameIn.getInput());
		serverIp.setText("Server Ip: " + Client.userIpAddress);
		Client.user = "ME";
	
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.act();
		uiStage.act();
		test.addAction(Actions.rotateBy(1));
		

		//System.out.println(client.getRecievedData());
		
		
		if (test.getBoundingRectangle().contains(Gdx.input.getX(), RoundWorld.unscaledHeight - Gdx.input.getY())) {
			if (Gdx.input.justTouched() && connectDone) {
				connectionThread = new Thread(connect);
				connectionThread.start();
				
				
			}
		}
		
		serverlessDebugOnClick();
		settingsButtonOnClick();
		
		/* check for title orbiting */
		for (int i = 0; i < Projectile.projs.size(); i++) {
			if(Projectile.projs.get(i).id.contains("TITLE_"))
			{
				/* each title char has id field of "TITLE_X" where X is which letter position it is */
				switch(Integer.parseInt(Projectile.projs.get(i).id.substring(6, 7)))
				{
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					if (Projectile.projs.get(i).center.x > RoundWorld.unscaledWidth / 2 - 128) {
						Projectile.projs.get(i).orbit(RoundWorld.unscaledWidth / 2, RoundWorld.unscaledHeight / 2, 90 + 45, -0.01f, 128);
						
					}
					break;
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					if (Projectile.projs.get(i).center.x < RoundWorld.unscaledWidth / 2 + 128) {
						Projectile.projs.get(i).orbit(RoundWorld.unscaledWidth / 2, RoundWorld.unscaledHeight / 2, 0, -0.01f, 128);
					}
					break;
				}
			}
		}
		
		/*animates image*/
		imgAnim();
	
		/* key handling */
		if (Gdx.input.isKeyJustPressed(Keys.U)) {
			username.setColor(Color.FOREST);
			serverIp.setColor(Color.YELLOW);
			Gdx.input.setInputProcessor(usernameIn);
			serverIp.clear();
		}
		if (Gdx.input.isKeyJustPressed(Keys.I)) {
			username.setColor(Color.YELLOW);
			serverIp.setColor(Color.FOREST);
			Gdx.input.setInputProcessor(ipIn);
			usernameIn.clear();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (Client.isConnected()) {
				Client.close();
			}
			
			Gdx.app.exit();
		}
		
		//countdown anim
		if (connectAnimCountdown > 0) {
			connectAnimCountdown--;
			test.scaleBy(3, 3);
		}
		
		//start game
		if (Client.isConnected() && connectAnimCountdown == 0) {
			try {
				connectionThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			GameplayScreen g = new GameplayScreen(this.game);
			this.game.setScreen(g);
		}
		if (ipIn.getInput() != null && ipIn.getInput() != "") {
			Client.userIpAddress = ipIn.getInput();
			AssetManager.saveIp(ipIn.getInput());
		}
		
		
		mainStage.getViewport().apply();
		mainStage.draw();
		
		uiStage.getViewport().apply();
		uiStage.draw();
	}
	
	/* moves elements back to initial positions (used at scaling change) */
	public void resetPositions()
	{
		test.setCenter(RoundWorld.unscaledWidth / 2, RoundWorld.unscaledHeight / 2);
		textBase.setPosition(20, 20);
		username.setPosition(10, RoundWorld.unscaledHeight - 25);
		serverIp.setPosition(10, RoundWorld.unscaledHeight - 50);
		serverlessDebugButton.setBounds(10, 10, 64 * 2, 16 * 2);
		settingsButton.setBounds(RoundWorld.unscaledWidth - (10 + 64 * 2), 10, 64*2, 16*2);
		usernameIn.setPosition(140, RoundWorld.unscaledHeight - 17);
		ipIn.setPosition(10, RoundWorld.unscaledHeight - 100);
	}
	
	
	/* resizes elements based on current scaling criteria */
	public void resetSizing()
	{
		test.setScale(3);
		username.setFontScale(2);
		serverIp.setFontScale(2);
		usernameIn.setScale(2);
		ipIn.setScale(2);
	}
	
	
	/* adds elements to mainStage */
	public void addElementsToStage()
	{
		textBase.addActor(text);
		mainStage.addActor(test);
		mainStage.addActor(textBase);
		mainStage.addActor(username);
		mainStage.addActor(serverIp);		
		mainStage.addActor(serverlessDebugButton);
		mainStage.addActor(settingsButton);
		mainStage.addActor(usernameIn);
		mainStage.addActor(ipIn);
	}
	
	
	/* restart title animation */
	public void resetTitleAnimation()
	{
		clearTitleBullets();
		start_title_animation();
	}
	
	
	private void start_title_animation()
	{
		Projectile.spawnBullet(mainStage, new Vector2(-70 * 0, RoundWorld.unscaledHeight / 2), 1.5f, 0, 0, 0, AssetManager.getTexture("D"), titleColor, "TITLE_4");								
		Projectile.spawnBullet(mainStage, new Vector2(-70 * 1, RoundWorld.unscaledHeight / 2), 1.5f, 0, 0, 0, AssetManager.getTexture("N"), titleColor, "TITLE_3");	
		Projectile.spawnBullet(mainStage, new Vector2(-70 * 2, RoundWorld.unscaledHeight / 2), 1.5f, 0, 0, 0, AssetManager.getTexture("U"), titleColor, "TITLE_2");	
		Projectile.spawnBullet(mainStage, new Vector2(-70 * 3, RoundWorld.unscaledHeight / 2), 1.5f, 0, 0, 0, AssetManager.getTexture("O"), titleColor, "TITLE_1");	
		Projectile.spawnBullet(mainStage, new Vector2(-70 * 4, RoundWorld.unscaledHeight / 2), 1.5f, 0, 0, 0, AssetManager.getTexture("R"), titleColor, "TITLE_0");	
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.unscaledWidth + 70 * 0, RoundWorld.unscaledHeight / 2), -1.5f, 0, 0, 0, AssetManager.getTexture("W"), titleColor, "TITLE_5");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.unscaledWidth + 70 * 1, RoundWorld.unscaledHeight / 2), -1.5f, 0, 0, 0, AssetManager.getTexture("O"), titleColor, "TITLE_6");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.unscaledWidth + 70 * 2, RoundWorld.unscaledHeight / 2), -1.5f, 0, 0, 0, AssetManager.getTexture("R"), titleColor, "TITLE_7");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.unscaledWidth + 70 * 3, RoundWorld.unscaledHeight / 2), -1.5f, 0, 0, 0, AssetManager.getTexture("L"), titleColor, "TITLE_8");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.unscaledWidth + 70 * 4, RoundWorld.unscaledHeight / 2), -1.5f, 0, 0, 0, AssetManager.getTexture("D"), titleColor, "TITLE_9");
	}
	
	private void clearTitleBullets()
	{
		for (int i = 0; i < Projectile.projs.size(); i++) {
			if(Projectile.projs.get(i).id.contains("TITLE_"))
			{
				Projectile.projs.get(i).remove();
			}
		}
	}
	
	private void imgAnim() {
		if (scaleVal > 0) {
			test.scaleBy(0.015f, 0.015f);
			scaleVal++;
		} else if (scaleVal < 0) {
			test.scaleBy(-0.015f, -0.015f);
			scaleVal--;
		}
		
		if (scaleVal > 10) {
			scaleVal = -1;
		} else if (scaleVal < -10) {
			scaleVal = 1;
		}
	}
	
	private void serverlessDebugOnClick() {
		if (serverlessDebugButton.getBoundingRectangle().contains(Gdx.input.getX() / RoundWorld.scale, RoundWorld.unscaledHeight - Gdx.input.getY() / RoundWorld.scale)) {
			if (Gdx.input.justTouched()) {
				RoundWorld.serverless = true;
				System.out.println("Going serverless");
				this.game.setScreen(new GameplayScreen(this.game));
			}
		}
	}
	
	private void settingsButtonOnClick() {
		if (settingsButton.getBoundingRectangle().contains(Gdx.input.getX() / RoundWorld.scale, RoundWorld.unscaledHeight - Gdx.input.getY() / RoundWorld.scale)) {
			if (Gdx.input.justTouched()) {
				if(this.settingsOpened == false)
				{
					this.settings = ((RoundWorld) game).getSettingsScreen();
					this.settings.create();
					this.settingsOpened = true;
				}
				
				this.game.setScreen(settings);
			}
		}
	}

	
	private Runnable connect = new Runnable() {
		
		
		@Override
		public void run() {
			//show connect
			connectDone = false;
			text.setColor(Color.FOREST);
			text.setText("Connecting...");
			textBase.addAction(Actions.fadeIn(1f));
			
			try {
				//create client only if is null
				if (!Client.isConnected()) {

					Client.init(Client.userIpAddress, 7777);
					
					//send hello
					Event e = new Event("client_connect");
					e.addArg("client_name", Client.user);
					Client.sendRequest(e.generate());
					System.out.println("Sending Connect Message");
				
					connectAnimCountdown = 30;
				}
			
			} catch (Exception e) {
				//connect fail
				text.setColor(Color.RED);
				text.setText("Connection Failed");
				textBase.addAction(Actions.fadeOut(1));
				System.out.println("Connection failed: " + e.getMessage());
			}
		
			
			connectDone = true;

		}

	};

	private Thread connectionThread;

	
	/*
	 * Updates viewport fields to account for new window / scaled dimensions
	 */
	public void updateViewports()
	{
		mainStage.getViewport().setWorldSize(RoundWorld.unscaledWidth, RoundWorld.unscaledHeight);
		mainStage.getViewport().update(RoundWorld.width, RoundWorld.height, true);
		uiStage.getViewport().setWorldSize(RoundWorld.unscaledWidth / RoundWorld.scale, RoundWorld.unscaledHeight / RoundWorld.scale);
		uiStage.getViewport().update(RoundWorld.width, RoundWorld.height, true);
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		((RoundWorld) game).adaptScalingToWindow();
		resetTitleAnimation();
		resetPositions();
		resetSizing();
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
