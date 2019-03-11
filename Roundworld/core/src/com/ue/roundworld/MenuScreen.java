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

public class MenuScreen implements Screen{
	
	public Stage mainStage;
	public Stage uiStage;
	public Game game;
		
	public BaseActor test;
	public BaseActor textBase = new BaseActor(Utils.emptyTexture);
	private TextInput usernameIn;
	private Label username;
	private Projectile[] title;
	public Label text;
	public Client client;
	private int scaleVal = 1;
	
	private Color titleColor = Color.RED;
	
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
	
		test = new BaseActor("assets/RW_Icon_64.png");
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
		
		
		
		
		
		
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		usernameIn = new TextInput(10, RoundWorld.height - 50, null);
		usernameIn.setHideLog(true);
		mainStage.addActor(usernameIn);
		Gdx.input.setInputProcessor(usernameIn);
		
		Projectile.spawnBullet(mainStage, new Vector2(0, RoundWorld.height/2), 1.5f, 0, 0, 0, Utils.loadTexture("title/D"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(-64, RoundWorld.height/2), 1.5f, 0, 0, 0, Utils.loadTexture("title/N"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(-64 * 2, RoundWorld.height/2 ), 1.5f, 0, 0, 0, Utils.loadTexture("title/U"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(-64 * 3, RoundWorld.height/2), 1.5f, 0, 0, 0, Utils.loadTexture("title/O"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(-64 * 4, RoundWorld.height/2), 1.5f, 0, 0, 0, Utils.loadTexture("title/R"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.width, RoundWorld.height/2), -1.5f, 0, 0, 0, Utils.loadTexture("title/W"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.width + 64, RoundWorld.height/2), -1.5f, 0, 0, 0, Utils.loadTexture("title/O"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.width + 64 * 2, RoundWorld.height/2), -1.5f, 0, 0, 0, Utils.loadTexture("title/R"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.width + 64 * 3, RoundWorld.height/2), -1.5f, 0, 0, 0, Utils.loadTexture("title/L"), titleColor, "R");
		Projectile.spawnBullet(mainStage, new Vector2(RoundWorld.width + 64 * 4, RoundWorld.height/2), -1.5f, 0, 0, 0, Utils.loadTexture("title/D"), titleColor, "R");
	}
	
	public void render(float dt){
		mainStage.act();
		titleTheme.play();
		username.setText("Username: " + usernameIn.getLastAdd());
		Client.user = usernameIn.getLastAdd();
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		test.rotateBy(1);

		
		//System.out.println(client.getRecievedData());
		
		
		if (test.getBoundingRectangle().contains(Gdx.input.getX(), RoundWorld.height - Gdx.input.getY())) {
			if (Gdx.input.justTouched() && connectDone) {
				connectionThread = new Thread(connect);
				connectionThread.start();
				
				
			}
		}
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
		imgAnim();
		
		
		mainStage.draw();
		uiStage.draw();
	
		
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (client != null) {
				client.close();
			}
			
			Gdx.app.exit();
		}
		
		//countdown anim
		if (connectAnimCountdown > 0) {
			connectAnimCountdown--;
			test.scaleBy(3, 3);
		}
		
		//start game
		if (client != null && connectAnimCountdown == 0) {
			try {
				connectionThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			GameplayScreen g = new GameplayScreen(this.game, client);
			this.game.setScreen(g);
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
				if (client == null) {

					client = new Client("128.193.254.13", 1337);
					
					//send hello
					client.sendRequest(Command.generate(
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
