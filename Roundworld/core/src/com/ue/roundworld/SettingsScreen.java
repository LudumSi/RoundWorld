package com.ue.roundworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Parser;
import com.ue.roundworld.ui.TextInput;

public class SettingsScreen implements Screen {
	private Stage mainStage;
	private Stage uiStage;
	
	private Preferences storedPrefs;
	
	private Rectangle settingEntriesRect; /* panel settingEntries list is centered in */
	
	private Game game;
	private MenuScreen menu;
	
	private Map<String, SettingEntry> settingEntries; /* keeps track of selected settingEntries */
	
	public static String[][] resolutionOptions = {
			{"640x480", "800x600", "1280x960", "1440x1080", "2048x1536"}, 		/* 4:3 */
			{"1280x720", "1600x900", "1920x1080", "2560x1440", "3840x2160"},  	/* 16:9 */
			{"1280x800", "1440x900", "1680x1050", "1920x1200", "2560x1600"}}; 	/* 16:10 */
	public static String[] aspectRatios = {"4:3", "16:9", "16:10"};
	public static String[] displayModes = {"windowed", "fullscreen"};
	public static float[] scalingOptions = {.5f, .75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f, 3.0f, 4.0f};
	
	/*
	 * class:		 	SettingEntry
	 * description:		Stores elements held on single line of options list
	 * 						(the arrows, states, current value, etc).
	 * constructor:		Takes number of options for this setting
	 */
	class SettingEntry {
		public Integer val;
		public Integer num_options;
		public BaseActor left_arrow, right_arrow;
		public boolean show_left = true, show_right = true;
		public boolean left_active = false, right_active = false;
		public Label label;
		public Label valueLabel;
		
		public SettingEntry(Integer num_options) {
			this.num_options = num_options;
		}
	};
	
	private BaseActor applyButton = new BaseActor(AssetManager.getTexture("apply_button"));
	private BaseActor backButton = new BaseActor(AssetManager.getTexture("back_button"));
	
	private Texture left_arrow_active = AssetManager.getTexture("left_arrow_active");
	private Texture left_arrow_unactive = AssetManager.getTexture("left_arrow_unactive");
	private Texture right_arrow_active = AssetManager.getTexture("right_arrow_active");
	private Texture right_arrow_unactive = AssetManager.getTexture("right_arrow_unactive");
	
	private Label title;
	
	
	/* constructor */
	public SettingsScreen(Game g, MenuScreen menu, String prefsFileName){
		game = g;
		this.menu = menu;
		settingEntries = new HashMap<String, SettingEntry>();
		storedPrefs = Gdx.app.getPreferences(prefsFileName);
		
		/* create called when first opened */
	}
	
	
	public void create() {
		/* make mainStage */
		mainStage = new Stage(new ScalingViewport(Scaling.fit, RoundWorld.unscaledWidth, RoundWorld.unscaledHeight));
		
		/* add settingEntries entries allowed */
		settingEntries.put("DisplayMode", new SettingEntry(displayModes.length)); 			/* windowed, fullscreen */
		settingEntries.put("AspectRatio", new SettingEntry(aspectRatios.length)); 			/* 4:3, 16:9, 16:10 */
		settingEntries.put("Resolution", new SettingEntry(resolutionOptions[0].length)); 	/* 5 depending on aspect ratio */
		settingEntries.put("Scale", new SettingEntry(scalingOptions.length + 1)); 			/* custom, .5f, .75f, 1.0f, 1.25f,
		
		/* compile settingEntries panel */
		float w = RoundWorld.unscaledWidth / 2;
		float h = settingEntries.size() * 30;
		int i = 1;
		
		settingEntriesRect = new Rectangle((RoundWorld.unscaledWidth - w) / 2, (RoundWorld.unscaledHeight + h) / 2, w, h);
		
		/* add title */
		title = new Label("Settings", RoundWorld.font);
		title.setAlignment(Align.center);
		title.setColor(Color.YELLOW);
		mainStage.addActor(title);

		/* add entries */
		for (Entry<String, SettingEntry> entry : settingEntries.entrySet())  
		{
			/* get current value */
			entry.getValue().val = getStoredOrDefault(entry);
			
			/* make label */
			entry.getValue().label = new Label(entry.getKey() + "  ", RoundWorld.font);
			entry.getValue().label.setPosition(settingEntriesRect.x + w / 2 - entry.getValue().label.getWidth(), settingEntriesRect.y - 30 * i);
			entry.getValue().label.setAlignment(Align.right);
			entry.getValue().label.setFontScale(2);
			entry.getValue().label.setColor(Color.BLACK);
			mainStage.addActor(entry.getValue().label);
			
			/* current valueLabel */
			entry.getValue().valueLabel = new Label("   " + getStringRep(entry), RoundWorld.font);
			entry.getValue().valueLabel.setPosition(settingEntriesRect.x + w / 2, settingEntriesRect.y - 30 * i);
			entry.getValue().valueLabel.setAlignment(Align.left);
			entry.getValue().valueLabel.setFontScale(2);
			entry.getValue().valueLabel.setColor(Color.BLACK);
			mainStage.addActor(entry.getValue().valueLabel);
			
			/* add arrows */
			entry.getValue().left_arrow = new BaseActor(left_arrow_unactive);
			entry.getValue().right_arrow = new BaseActor(right_arrow_unactive);
			entry.getValue().left_arrow.setBounds(settingEntriesRect.x + w / 2 + 5, settingEntriesRect.y - 30 * i, 16, 16);
			entry.getValue().right_arrow.setBounds(settingEntriesRect.x + w / 2 + 180, settingEntriesRect.y - 30 * i, 16, 16);
			mainStage.addActor(entry.getValue().left_arrow);
			mainStage.addActor(entry.getValue().right_arrow);
			
			i++;
		}
		
		backButton.setBounds(5, 5, 64, 16);
		mainStage.addActor(backButton);
		
		applyButton.setBounds(RoundWorld.unscaledWidth - 5 - 64, 5, 64, 16);
		mainStage.addActor(applyButton);
	}
	
	
	public void render(float dt){
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainStage.act();
		
		backButtonOnClick();
		applyButtonOnClick();
		arrowsOnClick();
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			this.game.setScreen(this.menu);
		}
		
		mainStage.getViewport().apply();
		mainStage.draw();
	}
	
	
	/* Resets position and size, adding elements to reduce loops through */
	public void resetElements()
	{
		float w = RoundWorld.unscaledWidth / 2;
		float h = settingEntries.size() * 30;
		int i = 1;
		
		settingEntriesRect.set((RoundWorld.unscaledWidth - w) / 2, (RoundWorld.unscaledHeight + h) / 2, w, h);
		title.setPosition(settingEntriesRect.x + (w - title.getWidth()) / 2, settingEntriesRect.y + 15);
		title.setFontScale(3);
		mainStage.addActor(title);
		
		for (Entry<String, SettingEntry> entry : settingEntries.entrySet())  
		{
			entry.getValue().label.setPosition(settingEntriesRect.x + w / 2 - entry.getValue().label.getWidth(), settingEntriesRect.y - 30 * i);
			entry.getValue().label.setFontScale(2);
			entry.getValue().valueLabel.setPosition(settingEntriesRect.x + w / 2, settingEntriesRect.y - 30 * i);
			entry.getValue().valueLabel.setFontScale(2);
			entry.getValue().left_arrow.setBounds(settingEntriesRect.x + w / 2 + 5, settingEntriesRect.y - 30 * i, 16, 16);
			entry.getValue().right_arrow.setBounds(settingEntriesRect.x + w / 2 + 180, settingEntriesRect.y - 30 * i, 16, 16);
			mainStage.addActor(entry.getValue().label);
			mainStage.addActor(entry.getValue().valueLabel);
			
			i++;
		}
		
		backButton.setBounds(5, 5, 64, 16);
		mainStage.addActor(backButton);
		
		applyButton.setBounds(RoundWorld.unscaledWidth - 5 - 64, 5, 64, 16);
		mainStage.addActor(applyButton);
	}
	
	
	/* resets element positions and sizes but doesn't add (reduces computation) */
	public void resetPositionsAndSizing()
	{
		float w = RoundWorld.unscaledWidth / 2;
		float h = settingEntries.size() * 30;
		int i = 1;
		
		settingEntriesRect.set((RoundWorld.unscaledWidth - w) / 2, (RoundWorld.unscaledHeight + h) / 2, w, h);
		title.setPosition(settingEntriesRect.x + (w - title.getWidth()) / 2, settingEntriesRect.y + 15);
		title.setFontScale(3);
		
		for (Entry<String, SettingEntry> entry : settingEntries.entrySet())  
		{
			entry.getValue().label.setPosition(settingEntriesRect.x + w / 2 - entry.getValue().label.getWidth(), settingEntriesRect.y - 30 * i);
			entry.getValue().label.setFontScale(2);
			entry.getValue().valueLabel.setPosition(settingEntriesRect.x + w / 2, settingEntriesRect.y - 30 * i);
			entry.getValue().valueLabel.setFontScale(2);
			entry.getValue().left_arrow.setBounds(settingEntriesRect.x + w / 2 + 5, settingEntriesRect.y - 30 * i, 16, 16);
			entry.getValue().right_arrow.setBounds(settingEntriesRect.x + w / 2 + 180, settingEntriesRect.y - 30 * i, 16, 16);
			
			i++;
		}
		
		backButton.setBounds(5, 5, 64, 16);
		applyButton.setBounds(RoundWorld.unscaledWidth - 5 - 64, 5, 64, 16);
	}
	
	
	/* moves elements back to initial positions (used at scaling change) */
	public void resetPositions()
	{
		float w = RoundWorld.unscaledWidth / 2;
		float h = settingEntries.size() * 30;
		int i = 1;
		
		settingEntriesRect.set((RoundWorld.unscaledWidth - w) / 2, (RoundWorld.unscaledHeight + h) / 2, w, h);
		title.setPosition(settingEntriesRect.x + (w - title.getWidth()) / 2, settingEntriesRect.y + 15);
		
		for (Entry<String, SettingEntry> entry : settingEntries.entrySet())  
		{
			entry.getValue().label.setPosition(settingEntriesRect.x + w / 2 - entry.getValue().label.getWidth(), settingEntriesRect.y - 30 * i);
			entry.getValue().valueLabel.setPosition(settingEntriesRect.x + w / 2, settingEntriesRect.y - 30 * i);
			entry.getValue().left_arrow.setBounds(settingEntriesRect.x + w / 2 + 5, settingEntriesRect.y - 30 * i, 16, 16);
			entry.getValue().right_arrow.setBounds(settingEntriesRect.x + w / 2 + 180, settingEntriesRect.y - 30 * i, 16, 16);
			
			i++;
		}
		
		backButton.setBounds(5, 5, 64, 16);
		applyButton.setBounds(RoundWorld.unscaledWidth - 5 - 64, 5, 64, 16);
	}
	

	/* resizes elements based on current scaling criteria */
	public void resetSizing()
	{
		title.setFontScale(3);
		
		for (Entry<String, SettingEntry> entry : settingEntries.entrySet())  
		{
			entry.getValue().label.setFontScale(2);
			entry.getValue().valueLabel.setFontScale(2);
		}
	}
	
	
	/* adds elements to the stages */
	public void addElementsToStage()
	{
		mainStage.addActor(title);
		
		for (Entry<String, SettingEntry> entry : settingEntries.entrySet())  
		{
			mainStage.addActor(entry.getValue().label);
			mainStage.addActor(entry.getValue().valueLabel);
		}
		
		mainStage.addActor(backButton);
		mainStage.addActor(applyButton);
	}	
	
	
	/* applies the settingEntries as listed in the preferences file */
	public void applySettingsFromFile()
	{
		/* change game fields */
		RoundWorld.width = storedPrefs.getInteger("res_w");
		RoundWorld.height = storedPrefs.getInteger("res_h");
			RoundWorld.scale = storedPrefs.getFloat("Scale");
		((RoundWorld) game).enforce_scaling();
		
		/* re-make window */
		launchWindow();
	}
	
	
	private void backButtonOnClick() {
		if (backButton.getBoundingRectangle().contains(Gdx.input.getX() / RoundWorld.scale, RoundWorld.unscaledHeight - Gdx.input.getY() / RoundWorld.scale)) {
			if (Gdx.input.justTouched()) {
				this.game.setScreen(this.menu);
			}
		}
	}
	
	
	private void applyButtonOnClick() {
		if (applyButton.getBoundingRectangle().contains(Gdx.input.getX() / RoundWorld.scale, RoundWorld.unscaledHeight - Gdx.input.getY() / RoundWorld.scale)) {
			if (Gdx.input.justTouched()) {
				/* store stuff */
				for(Entry<String, SettingEntry> entry : settingEntries.entrySet())
				{
					if(entry.getKey().equals("Resolution"))
					{
						/* store separate boys */
						String[] res_strings = getStringRep(entry).split("x", -1);
						storedPrefs.putInteger("res_w", Integer.parseInt(res_strings[0]));
						storedPrefs.putInteger("res_h", Integer.parseInt(res_strings[1]));
					}
					else if(entry.getKey().equals("Scale"))
					{
						/* store auto or preset */
						storedPrefs.putFloat("Scale", (entry.getValue().val == 0) ? (0) : (scalingOptions[entry.getValue().val - 1]));
					}
					else
					{
						storedPrefs.putInteger(entry.getKey(), entry.getValue().val);
					}
				}
				
				storedPrefs.flush();
				applySettingsFromFile();
				resize(0, 0);
			}
		}
	}
	
	
	private void arrowsOnClick() {
		for(Entry<String, SettingEntry> entry : settingEntries.entrySet())
		{
			/* left arrow */
			if(entry.getValue().show_left == true)
			{
				if (entry.getValue().left_arrow.getBoundingRectangle().contains(Gdx.input.getX() / RoundWorld.scale, RoundWorld.unscaledHeight - Gdx.input.getY() / RoundWorld.scale))
				{
					if(entry.getValue().left_active == false)
					{
						/* hover stuff */
						entry.getValue().left_arrow.setTexture(left_arrow_active);
						entry.getValue().left_active = true;
					}
					
					if (Gdx.input.justTouched()) {
						/* clicked stuff */
						entry.getValue().val = (entry.getValue().val <= 0) ? (entry.getValue().num_options - 1) : (entry.getValue().val - 1);
						entry.getValue().valueLabel.setText("   " + getStringRep(entry));
						
						if(entry.getKey().equals("AspectRatio"))
						{
							/* update resolution too */
							settingEntries.get("Resolution").valueLabel.setText("   " + getStringRep("Resolution"));
						}
					}
				}
				else if (entry.getValue().left_active == true)
				{
					/* not hovering */
					entry.getValue().left_arrow.setTexture(left_arrow_unactive);
					entry.getValue().left_active = false;
				}
			}
		
			/* right arrow */
			if(entry.getValue().show_right == true)
			{
				if (entry.getValue().right_arrow.getBoundingRectangle().contains(Gdx.input.getX() / RoundWorld.scale, RoundWorld.unscaledHeight - Gdx.input.getY() / RoundWorld.scale))
				{
					if(entry.getValue().right_active == false)
					{
						/* hover stuff */
						entry.getValue().right_arrow.setTexture(right_arrow_active);
						entry.getValue().right_active = true;
					}
					
					if (Gdx.input.justTouched()) {
						/* clicked stuff */
						entry.getValue().val = (entry.getValue().val >= entry.getValue().num_options - 1) ? (0) : (entry.getValue().val + 1);
						entry.getValue().valueLabel.setText("   " + getStringRep(entry));
						
						if(entry.getKey().equals("AspectRatio"))
						{
							/* update resolution too */
							settingEntries.get("Resolution").valueLabel.setText("   " + getStringRep("Resolution"));
						}
					}
				}
				else if (entry.getValue().right_active == true)
				{
					/* not hovering */
					entry.getValue().right_arrow.setTexture(right_arrow_unactive);
					entry.getValue().right_active = false;
				}
			}
		}
	}
	
	
	/*
	 * Updates viewport fields to account for new window / scaled dimensions
	 */
	public void updateViewports()
	{
		mainStage.getViewport().setWorldSize(RoundWorld.unscaledWidth, RoundWorld.unscaledHeight);
		mainStage.getViewport().update(RoundWorld.width, RoundWorld.height, true);
	}
	
	
	/* returns string representation of current value */
	private String getStringRep(Entry<String, SettingEntry> entry)
	{
		if(entry.getKey().equals("Resolution"))
		{
			return resolutionOptions[settingEntries.get("AspectRatio").val][entry.getValue().val];
		}
		else if(entry.getKey().equals("AspectRatio"))
		{
			return aspectRatios[entry.getValue().val];
		}
		else if(entry.getKey().equals("DisplayMode"))
		{
			return displayModes[entry.getValue().val];
		}
		else if(entry.getKey().equals("Scale"))
		{
			if(entry.getValue().val == 0)
			{
				return "auto";
			}
			else
			{
				return String.valueOf(scalingOptions[entry.getValue().val - 1]);
			}
		}
		else
		{
			return "ERROR";
		}
	}
	
	
	/* returns string representation of current value */
	private String getStringRep(String key)
	{
		if(key.equals("Resolution"))
		{
			return resolutionOptions[settingEntries.get("AspectRatio").val][settingEntries.get(key).val];
		}
		else if(key.equals("AspectRatio"))
		{
			return aspectRatios[settingEntries.get(key).val];
		}
		else if(key.equals("DisplayMode"))
		{
			return displayModes[settingEntries.get(key).val];
		}
		else if(key.equals("Scale"))
		{
			if(settingEntries.get(key).val == 0)
			{
				return "custom";
			}
			else if (settingEntries.get(key).val == scalingOptions.length+2)
			{
				return "automatic";
			}
			else
			{
				return String.valueOf(scalingOptions[settingEntries.get(key).val - 1]);
			}
		}
		else
		{
			return "ERROR";
		}
	}
	
	
	/* gets value for this entry from the preference file or default */
	public void resetAllToStoredOrDefault()
	{
		for(Entry<String, SettingEntry> entry : settingEntries.entrySet())
		{
			getStoredOrDefault(entry);
		}
	}
	
	
	/* gets value for this entry from the preference file or default */
	private Integer getStoredOrDefault(Entry<String, SettingEntry> entry)
	{
		Integer curr = 0;
		
		if(!storedPrefs.contains(entry.getKey()) && !entry.getKey().equals("Resolution"))
		{
			/* put new one */
			curr = resetToDefault(entry.getKey());
		}
		else if(entry.getKey().equals("Resolution"))
		{
			curr = 0;
			
			for(int i = 0; i < resolutionOptions[0].length; i++)
			{
				if(Integer.parseInt(resolutionOptions[settingEntries.get("AspectRatio").val][i].split("x")[0]) == storedPrefs.getInteger("res_w"))
				{
					curr = i;
					break;
				}
			}
		}
		else if(entry.getKey().equals("Scale"))
		{
			curr = 0; /* custom scale */
			
			float scale = storedPrefs.getFloat("Scale");
			for(int i = 0; i < scalingOptions.length; i++)
			{
				if(scalingOptions[i] == scale)
				{
					curr = i + 1;
					break;
				}
			}
		}
		else if(storedPrefs.contains(entry.getKey()))
		{
			curr = storedPrefs.getInteger(entry.getKey());
		}
		
		return curr;
	}
	
	
	/* Resets passed settingEntries entry to default and returns the Integer representation */
	private Integer resetToDefault(String key)
	{
		int result = 0;
		
		if(key.contains("DisplayMode"))
		{
			storedPrefs.putInteger("DisplayMode", 0);
			result = 0;
		}
		else if(key.contains("AspectRatio"))
		{
			storedPrefs.putInteger("AspectRatio", 1);
			result = 1;
		}
		else if(key.contains("Resolution"))
		{
			switch(storedPrefs.getInteger("AspectRatio"))
			{
			case 0:
				storedPrefs.putInteger("res_w", 800);
				storedPrefs.putInteger("res_h", 600);
				result = 1;
			case 1:
				storedPrefs.putInteger("res_w", 1920);
				storedPrefs.putInteger("res_h", 1080);
				result = 2;
			case 3:
				storedPrefs.putInteger("res_w", 1920);
				storedPrefs.putInteger("res_h", 1200);
				result = 3;
			}
		}
		else if(key.contains("Scale"))
		{
			storedPrefs.putFloat("Scale", 1f);
			result = 3;
		}
		
		storedPrefs.flush();
		return result;
	}

	
	/* returns true if the preference file exists */
	public boolean prefsFileExists(String fileName)
	{
		return !(Gdx.app.getPreferences(fileName).get().isEmpty());
	}
	
	
	/* launches window (app must have started first) */
	public void launchWindow()
	{
		Gdx.graphics.setWindowedMode(RoundWorld.width, RoundWorld.height);
		if(storedPrefs.getInteger("DisplayMode") == 1)
		{
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		resetPositionsAndSizing();
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
