package com.ue.roundworld;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.ue.roundworld.client.Client;

public class RoundWorld extends Game {
	
	public static LabelStyle font;
	public static boolean paused;

	private MenuScreen menuScreen;
	private SettingsScreen settingsScreen;
	private String prefsFileName = "settings";
	
	public static int height; 			/* window pixel height */
	public static int width; 			/* window pixel width */
	public static int unscaledHeight; 	/* ui width before scaling (coordinate system) */
	public static int unscaledWidth; 	/* ui height before scaling (coordinate system) */
	public static float scale = 1; 		/* scaling up to account for display size */
	public static float targetZoom = 1;	/* user controlled zoom level during game */
	public static float minZoom = .75f;
	public static float maxZoom = 1.5f;

	public static boolean serverless = false;

	
	@Override
	public void create() {
		/* setup font */
		// viewBorder = new Rectangle(1, 0, PS.viewWidth-1, PS.viewHeight-220);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fantasquesansmono-regular.otf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont theFont = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!*/
		font = new LabelStyle(theFont, Color.WHITE);
		
		/* setup input processor */
		Gdx.input.setInputProcessor(new InputProcess());
		
		/* load assets from folder "assets" */
		AssetManager.loadTextures(new File("assets"));
		
		/* make screens */
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this, menuScreen, prefsFileName);
		
		/* set defaults if file doesn't exist */
		if (!settingsScreen.prefsFileExists(prefsFileName))
		{
			settingsScreen.resetAllToDefault();
		}
		
		/* load settings, reset to defaults if needed */
		settingsScreen.resetAllToStoredOrDefault();
		
		/* read and enforce stored display settings */
		settingsScreen.applySettingsFromFile();
		enforce_scaling();
		
		/* set screen to the menu */
		setScreen(menuScreen);
	}
	
	
	@Override
	public void dispose() {
		if (Client.isConnected()) {
			Client.close();
		}
		super.dispose();
	}

	
	/*
	 * Sets scale to best value for dimensions
	 */
	public void autoScale()
	{
		/* @TODO for automatic scaling, uses 1 for now */
		RoundWorld.unscaledWidth = RoundWorld.width;
		RoundWorld.unscaledHeight = RoundWorld.height;
	}
	
	
	/*
	 * Embraces current window size, adapts to new dimensions
	 */
	public void adaptScalingToWindow() {
		RoundWorld.width = Gdx.graphics.getWidth();
		RoundWorld.height = Gdx.graphics.getHeight();
		RoundWorld.unscaledWidth = (int) (RoundWorld.width / RoundWorld.scale);
		RoundWorld.unscaledHeight = (int) (RoundWorld.height / RoundWorld.scale);
	}
	
	
	/* 
	 * Determines unscaled dimensions for the current width, height, and scale
	 */
	public void enforce_scaling() {
		if(RoundWorld.scale != 0)
		{
			RoundWorld.unscaledWidth = (int) (RoundWorld.width / RoundWorld.scale);
			RoundWorld.unscaledHeight = (int) (RoundWorld.height / RoundWorld.scale);
		}
		else
		{
			autoScale();
		}
	}
	
	
	/*
	 * Rounds scaling to closest valid option
	 */
	public void roundScalingToPreset()
	{
		int i = 0;
		while (SettingsScreen.scalingOptions[++i] <= RoundWorld.scale) {}
		RoundWorld.scale = (float) SettingsScreen.scalingOptions[i-1];
	}
	
	
	/*
	 * Returns this game's SettingsScreen
	 */
	public SettingsScreen getSettingsScreen()
	{
		return settingsScreen;
	}
	
	/* rounds scale to number of decimals - deprecated for now
		public void roundScalingTo(int num_decimals) {
			if(num_decimals >= 0)
			{
				RoundWorld.scale = (float) (Math.round(RoundWorld.scale * Math.pow(10, num_decimals)) / Math.pow(10, num_decimals));
			}
			else
			{
				RoundWorld.scale = 1;
			}
		}
	*/
	
	/* scales ui - deprecated for now
		public void scaleUpToScreenSize() {
			float w_scale_max = (float) java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / RoundWorld.unscaledWidth;
			float h_scale_max = (float) java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / RoundWorld.unscaledHeight;
			RoundWorld.scale = (float) ((w_scale_max < h_scale_max) ? (w_scale_max) : (h_scale_max));
			roundScalingToPreset();
			enforce_scaling();
		}
	*/
}
