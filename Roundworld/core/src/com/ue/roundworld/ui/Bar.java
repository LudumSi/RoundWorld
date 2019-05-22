package com.ue.roundworld.ui;

import com.badlogic.gdx.graphics.Texture;
import com.ue.roundworld.BaseActor;

public class Bar extends BaseActor{
	
	private BaseActor barVal;
	private BaseActor barLoss;
	private float ratio, lossRatio, lossFadeAmount, lossFadeTimer;
	
	private static final int LOSS_FADE_TIMER_MAX = 10;
	private static final float LOSS_FADE_RATIO_SECS = 0.10f;
	private static final float LOSS_FADE_RATIO_MAX = 1.0f;
	
	public Bar(Texture back, Texture loss, Texture fore) {
		super(back);
		barVal = new BaseActor(fore);
		barLoss = new BaseActor(loss);
		this.addActor(barLoss);
		this.addActor(barVal);
		ratio = 1.0f; 
	}
	
	public void act(float dt) {
		
		
		//health.region.setRegionWidth((int) (healthRatio * 256));
		//slice the bar to the ratio
		
		//TODO make this fancy
		
		if (lossFadeTimer < 0) {
			lossFadeTimer = 0;
		} else {
			lossFadeTimer -= dt * 60;
		}
			
		if(lossFadeTimer == 0) {
			lossRatio = (lossRatio - lossFadeAmount) < ratio ? ratio : lossRatio - lossFadeAmount;
		}
		
		barLoss.slice(0, 0, (int) (lossRatio * 256), (int) this.getHeight());
		barVal.slice(0, 0,(int) (ratio * 256), (int)this.getHeight());
		
		
		
	}
	
	/**
	 * Subtracts a percentage of max value from the bar
	 * @param amt the percentage to subtract
	 */
	public void sub(float amt) {
		ratio -= amt;
	
		if (ratio <= 0) {
			ratio = 0;
			lossFadeTimer = 0;
		}
		else {
			if(lossFadeTimer == 0) {
				lossFadeTimer = LOSS_FADE_TIMER_MAX;
			}else {
				lossFadeTimer = lossFadeTimer <= LOSS_FADE_TIMER_MAX - 5 ? lossFadeTimer + 5 : LOSS_FADE_TIMER_MAX;
			}
		}

		lossFadeAmount = (lossRatio - ratio) * LOSS_FADE_RATIO_SECS;
		lossFadeAmount = lossFadeAmount > LOSS_FADE_RATIO_MAX ? LOSS_FADE_RATIO_MAX : lossFadeAmount;
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
		
		if (ratio >= lossRatio) {
			lossRatio = ratio;
		}
		
		lossFadeAmount = (lossRatio - ratio) * LOSS_FADE_RATIO_SECS;
		lossFadeAmount = lossFadeAmount > LOSS_FADE_RATIO_MAX ? LOSS_FADE_RATIO_MAX : lossFadeAmount;
	}
	
}
