package com.ue.roundworld;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
	
	private static HashMap<String, Texture> textureLib = new HashMap<String, Texture>();
	
	public static void load_textures(File f) {
		if (f.isDirectory()) {
			load_textures(f);
		} else {
			if (f.getName().contains(".png")) {
				textureLib.put(f.getName().substring(0, f.getName().length() - 4), Utils.loadTexture(f.getPath()));
			}
			
		}
	}
	
	public static Texture get_texture(String s) {
		if (textureLib.containsKey(s)) {
			return Utils.missingTexture;
		}
		return textureLib.get(s);
	}
	
}
