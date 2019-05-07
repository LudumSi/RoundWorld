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
	private HashMap<Integer, BaseActor> renders = new HashMap<Integer, BaseActor>();
	public RenderManager(){
	
	}
	
	
	public boolean getRenders(Stage s) {
		Command com = Client.popParsedData();
		
		
		
		if (com != null && Command.verify(com, Component.Type.render, com.getNumComponents()) && com.get_id() == 0x002){
			for (int i = 0; i < com.getNumComponents(); i++) {
				BaseActor ba = new BaseActor(AssetManager.get_texture(com.get_component(Component.Type.render, i).getArg(0)));
				ba.setCenter(
						Float.parseFloat(com.get_component(Component.Type.render, i).getArg(1)),
						Float.parseFloat(com.get_component(Component.Type.render, i).getArg(2))
						);
				s.addActor(ba);
				renders.put(Integer.parseInt(com.get_component(Component.Type.render, i).getArg(3)), ba);
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
