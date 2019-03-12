package com.ue.roundworld.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ue.roundworld.AssetManager;
import com.ue.roundworld.BaseActor;

public class RenderManager{
	
	private static final int renderDist = 1000;
	private Client c;
	private HashMap<Integer, BaseActor> renders = new HashMap<Integer, BaseActor>();
	public RenderManager(Client c){
		this.c = c;
	}
	
	
	public boolean getRenders() {
		Command com = c.getParsedData();
		if (com.get_id() == 0xC001 && com.get_component(1, 0) != null) {
			for (int i = 0; i < com.components.size(); i++) {
				Texture t = AssetManager.get_texture(com.get_component(1, i).getArg("texture"));
				int x = Integer.parseInt(com.get_component(1, i).getArg("x"));
				int y = Integer.parseInt(com.get_component(1, i).getArg("y"));
				BaseActor ba = new BaseActor(t);
				ba.setCenter(x, y);
				renders.put(Integer.parseInt(com.get_component(1, i).getArg("id")), ba);
			}
			return true;
		}
		return false;
		
	}
	
	public void render(BaseActor player) {
		for (BaseActor b : renders.values()) {
			if (b.distanceTo(player.center.x, player.center.y) <= renderDist) {
				b.setVisible(true);
			} else {
				b.setVisible(false);
			}
		}
	}
	
}
