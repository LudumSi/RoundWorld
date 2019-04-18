package com.ue.roundworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;
import com.ue.roundworld.client.Parser;
import com.ue.roundworld.ui.TextInput;

public class MenuScreen implements Screen{
	
	public Stage mainStage;
	public Stage uiStage;
	public Game game;
		
	
	private BaseActor serverlessDebugButton = new BaseActor(AssetManager.get_texture("serverless_debug_button"));
	
	public BaseActor test;
	public BaseActor textBase = new BaseActor(Utils.emptyTexture);
	private TextInput usernameIn;
	private TextInput ipIn;
	
	private Label username;
	private Label serverIp;
	public Label text;
	
	
	private int scaleVal = 1;
	
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
	
		test = new BaseActor(AssetManager.get_texture("RW_Icon_64"));
		test.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		mainStage.addActor(test);
		test.scaleBy(2, 2);
		
		text = new Label("", RoundWorld.font);
		text.setFontScale(3);
		textBase.addActor(text);
		textBase.addAction(Actions.fadeOut(0));
		mainStage.addActor(textBase);
		textBase.setPosition(20, 20);
		
		username = new Label("Username: ", RoundWorld.font);
		username.setPosition(10, RoundWorld.height - 25);
		username.setColor(Color.YELLOW);
		mainStage.addActor(username);
		
		serverIp = new Label("Server Ip: ", RoundWorld.font);
		serverIp.setPosition(10, RoundWorld.height - 75);
		serverIp.setColor(Color.YELLOW);
		mainStage.addActor(serverIp);
		
		serverlessDebugButton.setPosition(5, 5);
		mainStage.addActor(serverlessDebugButton);
		
		
		String ip = AssetManager.loadIp();
		if (ip != null) {
			Client.userIpAddress = ip;
			
		}
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		usernameIn = new TextInput(10, RoundWorld.height - 50);
		mainStage.addActor(usernameIn);
		Gdx.input.setInputProcessor(usernameIn);
		
		ipIn = new TextInput(10, RoundWorld.height - 100);
		mainStage.addActor(ipIn);
		
		Projectile.spawnBullet(uiStage, new Vector2(0, RoundWorld.height/2), 1.5f, 0, 0, 0, AssetManager.get_texture("D"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(-64, RoundWorld.height/2), 1.5f, 0, 0, 0, AssetManager.get_texture("N"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(-64 * 2, RoundWorld.height/2 ), 1.5f, 0, 0, 0, AssetManager.get_texture("U"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(-64 * 3, RoundWorld.height/2), 1.5f, 0, 0, 0, AssetManager.get_texture("O"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(-64 * 4, RoundWorld.height/2), 1.5f, 0, 0, 0, AssetManager.get_texture("R"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(RoundWorld.width, RoundWorld.height/2), -1.5f, 0, 0, 0, AssetManager.get_texture("W"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(RoundWorld.width + 64, RoundWorld.height/2), -1.5f, 0, 0, 0, AssetManager.get_texture("O"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(RoundWorld.width + 64 * 2, RoundWorld.height/2), -1.5f, 0, 0, 0, AssetManager.get_texture("R"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(RoundWorld.width + 64 * 3, RoundWorld.height/2), -1.5f, 0, 0, 0, AssetManager.get_texture("L"), titleColor, "R");
		Projectile.spawnBullet(uiStage, new Vector2(RoundWorld.width + 64 * 4, RoundWorld.height/2), -1.5f, 0, 0, 0, AssetManager.get_texture("D"), titleColor, "R");
	
		test.addAction(Actions.fadeOut(0));
		test.addAction(Actions.sequence(
				Actions.delay(6f),
				Actions.parallel(
						Actions.fadeIn(0.5f),
						Actions.rotateBy(360, 0.5f)
						
						)
				
				
				));
				
				
				
		
	}
	
	public void render(float dt){
		
		titleTheme.play();
		username.setText("Username: " + usernameIn.getInput());
		serverIp.setText("Server Ip: " + Client.userIpAddress);
		Client.user = "ME";
	
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.act();
		uiStage.act();
		test.addAction(Actions.rotateBy(1));
		

		
		//System.out.println(client.getRecievedData());
		
		
		if (test.getBoundingRectangle().contains(Gdx.input.getX(), RoundWorld.height - Gdx.input.getY())) {
			if (Gdx.input.justTouched() && connectDone) {
				connectionThread = new Thread(connect);
				connectionThread.start();
				
				
			}
		}
		
		serverlessDebugOnClick();
		
		
		
		for (int i = 0; i < Projectile.projs.size(); i++) {
			
	
		

			if (i < 5) {
				if (Projectile.projs.get(i).center.x > RoundWorld.width/2 - 128) {
					Projectile.projs.get(i).orbit(RoundWorld.width/2, RoundWorld.height/2, 90 + 45, -0.01f, 128);
					
				}
			} else {
				if (Projectile.projs.get(i).center.x < RoundWorld.width/2 + 128) {
					Projectile.projs.get(i).orbit(RoundWorld.width/2, RoundWorld.height/2, 0, -0.01f, 128);
				}
			}
		}
		/*animates image*/
		//imgAnim();
		
		
		mainStage.draw();
		uiStage.draw();
	
		
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
		if (serverlessDebugButton.getBoundingRectangle().contains(Gdx.input.getX(), RoundWorld.height - Gdx.input.getY())) {
			if (Gdx.input.justTouched()) {
				RoundWorld.serverless = true;
				System.out.println("Going serverless");
				GameplayScreen g = new GameplayScreen(this.game);
				this.game.setScreen(g);
				
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
					Client.sendRequest(Command.generate(
							Command.Type.initConnect, 
							Component.generate(Component.Type.text, Client.user)));
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
