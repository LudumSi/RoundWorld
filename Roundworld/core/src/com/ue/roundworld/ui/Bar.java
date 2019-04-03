package com.ue.roundworld.ui;

import com.badlogic.gdx.graphics.Texture;
import com.ue.roundworld.BaseActor;

public class Bar extends BaseActor{
	
	private BaseActor barVal;
	private float ratio;
	
	public Bar(Texture back, Texture fore) {
		super(back);
		barVal = new BaseActor(fore);
		this.addActor(barVal);
		ratio = 1.0f; 
	}
	
	public void act(float dt) {
		
		
		//health.region.setRegionWidth((int) (healthRatio * 256));
		barVal.setWidth((float) (ratio * 256));
		
		
		
	}
	/**
	 * Subtracts a percentage of max value from the bar
	 * @param amt the percentage to subtract
	 */
	public void sub(float amt) {
		ratio -= amt;
	
		if (ratio <= 0) {
			ratio = 0;
		}
	}
	
	/**
	 * Adds a percentage of max value to the bar
	 * @param amt the percentage to add
	 */
	public void add(float amt) {
		ratio += amt;
	
		if (ratio >= 1) {
			ratio = 1;
		}
	}
	
}
