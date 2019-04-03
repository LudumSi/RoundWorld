package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Utils {
	
	
	public static Texture emptyTexture = Utils.loadTexture("assets/null.png");
	public static Texture missingTexture = new Texture(Gdx.files.internal("assets/missingTex.png"));
	
	public static Vector2 polarToRect(int r, double angle, Vector2 origin) {
		Vector2 v = new Vector2();
		
		v.x = (float) (r * Math.cos(Math.toRadians(angle))) + origin.x;
		v.y = (float) (r * Math.sin(Math.toRadians(angle))) + origin.y;

		return v;
	}

	public static void insertionSort(int[] ar) {
		for (int i = 1; i < ar.length; i++) {
			int index = ar[i];
			int j = i;
			while (j > 0 && ar[j - 1] > index) {
				ar[j] = ar[j - 1];
				j--;
			}
			ar[j] = index;
		}

	}
	


	public static Texture loadTexture(String path) {
		try {
			Texture t = new Texture(Gdx.files.internal(path));
			
			String s = "hello";
		
			return t;

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");
			Texture t = new Texture(Gdx.files.internal("assets/missingTex.png"));
			return t;
		}
	}
	
	public static Animation<TextureRegion> loadAnimation(String path, int cols, int rows){
		Texture sheet = Utils.loadTexture(path);
		TextureRegion[][] map = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		TextureRegion[] frames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = map[i][j];
			}
		}
		return new Animation<TextureRegion>(0.025f, frames);
	}

	

	public static float pointAt(float x, float y, float x2, float y2) {

		float yDiff = y - y2;
		float xDiff = x - x2;
		float newAngle = MathUtils.radiansToDegrees * MathUtils.atan2(yDiff, xDiff) + 180;


		return newAngle;

	}

	public static Vector2 convertVel(float angle) {
	

		return new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
	}

	public static float distanceTo(float x, float y, float x2, float y2) {

		return  (float) Math.hypot(Math.abs(x2 - x), Math.abs(y2 - y));
	}
	
	

	public static int getDigits(int i) {
		int count = 0;
		while (i > 0) {
			i/= 10;
			count++;
		}
		
		return count;
	}

}
