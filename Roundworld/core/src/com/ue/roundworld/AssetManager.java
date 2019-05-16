package com.ue.roundworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
	
	private static HashMap<String, Texture> textureLib = new HashMap<String, Texture>();
	
	/**
	 * Recursively loads ALL the textures in a given file, reads through subdirectories
	 * @param f the file to load textures from
	 */
	public static void loadTextures(File f) {
		if (f.isDirectory()) {
			
			System.out.println(f.getName());
			
			for (File fi : f.listFiles()) {
				loadTextures(fi);
			}
		} else {
			if (f.getName().contains(".png")) {
				String fName = f.getName().substring(0, f.getName().length() - 4);
				System.out.println("Adding " + fName + " to texLib");
				textureLib.put(fName, new Texture(Gdx.files.internal(f.getPath())));
			}
			
		}
	}
	
	/**
	 * gets a texture from the texture library
	 * @param s the name of the texture to obtain, without file extensions
	 * @return a texture with the given name
	 */
	public static Texture getTexture(String s) {
		if (!textureLib.containsKey(s)) {
			System.out.println("Error: Could not find: " + s + " in texLib, substituting...");
			return Utils.missingTexture;
		}
		return textureLib.get(s);
	}
	
	public static void saveIp(String ip) {
		try {
			FileWriter wr = new FileWriter("assets/ip.txt", false);
			wr.write(ip);
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

	public static String loadIp() {
		try {
			FileReader rr = new FileReader("assets/ip.txt");
			BufferedReader br = new BufferedReader(rr);
			String line = br.readLine();
			br.close();
			return line;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}	
	
	


