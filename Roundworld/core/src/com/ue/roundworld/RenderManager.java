package com.ue.roundworld;

import java.util.ArrayList;

import com.badlogic.gdx.utils.JsonValue;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class RenderManager {
	
	private static ArrayList<BaseActor> groundLayer = new ArrayList<BaseActor>();
	private static ArrayList<BaseActor> surfaceLayer = new ArrayList<BaseActor>();
	private static ArrayList<BaseActor> roofLayer = new ArrayList<BaseActor>();
	private static final int renderDist = 1000;
	
	
	public static void getRenders(String area) {
		//request renders
		Event e = new Event("render_request");
		e.addArg("area", area);
		Client.sendRequest(e.generate());
		
		//clear layers
		groundLayer.clear();
		surfaceLayer.clear();
		roofLayer.clear();
		boolean done = false;
	
		while(!done)  {
			//get and verify data
			Event ev = Client.popParsedData();
			if (Event.verify(ev, "render_list")) {
					//load ground layer
					JsonValue[] ground = ev.getArray("ground");
				
					for (int i = 0; i < ground.length; i++) {
						//create base actors and add to ground layer.
						BaseActor b = new BaseActor(AssetManager.getTexture(ground[i].getString("texture")));
						b.setPosition(ground[i].getInt("x"), ground[i].getInt("y"));
						groundLayer.add(b);
					}
					//load surface layer
					JsonValue[] surface = ev.getArray("surface");
					
					for (int i = 0; i < surface.length; i++) {
						//create base actors and add to ground layer.
						BaseActor b = new BaseActor(AssetManager.getTexture(surface[i].getString("texture")));
						b.setPosition(surface[i].getInt("x"), surface[i].getInt("y"));
						surfaceLayer.add(b);
					}
					//load roof layer
					JsonValue[] roof = ev.getArray("roof");
					
					for (int i = 0; i < roof.length; i++) {
						//create base actors and add to ground layer.
						BaseActor b = new BaseActor(AssetManager.getTexture(roof[i].getString("texture")));
						b.setPosition(roof[i].getInt("x"), roof[i].getInt("y"));
						surfaceLayer.add(b);
					}
			}
			done = true;
				
		}
	}
		
		
	
	
	public static void render(Player p) {
		for (BaseActor render : groundLayer) {
			if (render.distanceTo(p.center.x, p.center.y) < renderDist) {
				render.setVisible(true);
			} else {
				render.setVisible(false);
			}
		}
	}
	
}
