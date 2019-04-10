package com.ue.roundworld;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.ue.roundworld.client.Client;

public class RoundWorld extends Game {
	

	
	public static LabelStyle font;
	public static boolean paused;


	public static int height = 700;
	public static int width = 800;
	

	public static Rectangle window = new Rectangle(0, 0, width, height);
	
	public static boolean serverless = false;
	
	@Override
	public void create() {
	
	
		// viewBorder = new Rectangle(1, 0, PS.viewWidth-1, PS.viewHeight-220);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fantasquesansmono-regular.otf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont theFont = generator.generateFont(parameter); // font size 12
																// pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!*/
		
		Gdx.input.setInputProcessor(new InputProcess());
		
		
		AssetManager.load_textures(new File("assets"));
		
		font = new LabelStyle(theFont, Color.WHITE);
		MenuScreen ms = new MenuScreen(this);
		
		setScreen(ms);
		
	}
	
	@Override
	public void dispose() {
		if (Client.isConnected()) {
			Client.close();
		}
		super.dispose();
	}

	

	
	
}
