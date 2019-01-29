package com.ue.roundworld.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ue.roundworld.RoundWorld;


public class DesktopLauncher {
	public static void main (String[] arg) {
		RoundWorld theGame = new RoundWorld();
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Round World";
		//cfg.fullscreen = true;
		//cfg.addIcon("assets/player.png", FileType.Internal);
	
		cfg.addIcon("assets/icon.png", FileType.Internal);

	
		LwjglApplication launcher = new LwjglApplication(theGame, cfg);
	
	}
}
