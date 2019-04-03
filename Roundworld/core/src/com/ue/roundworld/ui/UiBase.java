package com.ue.roundworld.ui;

import com.ue.roundworld.BaseActor;
import com.ue.roundworld.RoundWorld;
import com.ue.roundworld.Utils;

public class UiBase extends BaseActor{
	
	private Bar phb;
	private Bar pmb;
	
	public UiBase() {
		super(Utils.emptyTexture);
		phb = new Bar(Utils.loadTexture("ui/health_bar_back"), Utils.loadTexture("ui/health_bar"));
		pmb = new Bar(Utils.loadTexture("ui/mana_bar_back"), Utils.loadTexture("ui/mana_bar"));
		phb.setPosition(0, RoundWorld.height - 16);
		pmb.setPosition(0, RoundWorld.height - 16 - 8);
		this.addActor(phb);
		this.addActor(pmb);
		pmb.sub(1);
	}
	
	public void act(float dt) {
		super.act(dt);
		phb.sub(0.001f);
		pmb.add(0.001f);
	}
}
