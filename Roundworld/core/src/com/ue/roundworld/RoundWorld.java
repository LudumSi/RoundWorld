package com.ue.roundworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class RoundWorld extends Game {
	

	
	public static Rectangle viewBorder;
	public static LabelStyle font;
	public static boolean paused;



	
	
	@Override
	public void create() {
	
	
		// viewBorder = new Rectangle(1, 0, PS.viewWidth-1, PS.viewHeight-220);
		/*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/AndromedaTV.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont theFont = generator.generateFont(parameter); // font size 12
																// pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!*/
		
		Gdx.input.setInputProcessor(new InputProcess());
		
		font = new LabelStyle(new BitmapFont(), Color.WHITE);
		GameplayScreen ms = new GameplayScreen(this);
		
		setScreen(ms);
	}


	
	
}
