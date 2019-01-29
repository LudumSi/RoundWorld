package com.ue.roundworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class InputProcess implements InputProcessor {
	public char lastCharTyped;

	private static boolean leftMouseDown;
	private static boolean rightMouseDown;
	private static int scroll;



	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		lastCharTyped = character;
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			leftMouseDown = true;
		} else if (button == 1) {
			rightMouseDown = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			leftMouseDown = false;
		} else if (button == 1) {
			rightMouseDown = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// increase speed from 1.2 to 6, a range between viewing a single planet
		// and seeing its surroundings
		// zoomammount *= 1 + .5 * sensitivity; this makes sure that the
		// multiplier is always increasing or decreasing (compared to 1.05 *
		// sensitivity, which would reverse the scrolling past 1/1.05
		// sensitivity)

		
		scroll += amount;
		return false;
	}

	public static boolean leftMouseClicked() {
		return leftMouseDown;
	}

	public static boolean rightMouseClicked() {
		return rightMouseDown;
	}
	
	public static int scroll() {
		return scroll;
	}
	public static void resetScroll() {
		scroll = 0;
	}

}
