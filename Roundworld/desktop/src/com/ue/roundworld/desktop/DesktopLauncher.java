package com.ue.roundworld.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ue.roundworld.RoundWorld;


public class DesktopLauncher {
	public static void main (String[] arg) {
		RoundWorld theGame = new RoundWorld();
		
		/* handle window configuration stuff */
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Round World";
		cfg.resizable = false;
		// cfg.addIcon("assets/player.png", FileType.Internal);
		
		cfg.addIcon("assets/textures/RW_Icon_32.png", FileType.Internal);
	
		/* create launcher (go!) */
		LwjglApplication launcher = new LwjglApplication(theGame, cfg);
	}
}
