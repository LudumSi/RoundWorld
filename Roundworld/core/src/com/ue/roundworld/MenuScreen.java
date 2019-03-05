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
	public Label text;
	public Client client;
	private int scaleVal = 1;
	
	private int connectAnimCountdown = -1;
	
	boolean connectDone = true;
	
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
		mainStage.addActor(textBase);
		textBase.setPosition(20, 20);
		//TextInput listener = new TextInput();
		//Gdx.input.getTextInput(listener, "Enter ip", "Insert server ip here", "hint hint nudge nudge");
		
		Gdx.input.setInputProcessor(new InputProcess());
		
		
	}
	
	public void render(float dt){
		
		titleTheme.play();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		test.rotateBy(1);

		
	
		//System.out.println(client.getRecievedData());
		
		
		if (test.getBoundingRectangle().contains(Gdx.input.getX(), RoundWorld.height - Gdx.input.getY())) {
			if (InputProcess.leftMouseClicked() && connectDone) {
				connectionThread = new Thread(connect);
				connectionThread.start();
				
				
			}
		}
	
		imgAnim();
		
		mainStage.draw();
		uiStage.draw();
	
		
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (client != null) {
				client.close();
			}
			
			Gdx.app.exit();
		}
		
		if (connectAnimCountdown > 0) {
			connectAnimCountdown--;
			test.scaleBy(3, 3);
		}
		
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
			connectDone = false;
			text.setColor(Color.GREEN);
			text.setText("Connecting...");
			textBase.addAction(Actions.fadeIn(1f));
			
			try {
				if (client == null) {
					client = new Client("128.193.254.13", 1337);
					/*send user name*/
					Client.user = "JIMGRIND";
					client.sendRequest(Command.generate(
							Command.Type.initConnect, 
							Component.generate(Component.Type.text, Client.user)));
				
					connectAnimCountdown = 30;
				}
			
			} catch (Exception e) {
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
