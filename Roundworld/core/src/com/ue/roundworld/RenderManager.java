package com.ue.roundworld;

import java.util.ArrayList;

import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class RenderManager {
	
	private static ArrayList<BaseActor> renders = new ArrayList<BaseActor>();
	private static final int renderDist = 1000;
	
	
	public static void getRenders(String area) {
		Event e = new Event("render_request");
		e.addArg("area", area);
		Client.sendRequest(e.generate());
		renders.clear();
		while(renders.isEmpty()) {
			Event ev = Client.popParsedData();
			if (Event.verify(ev, "render_list")) {
				//TODO
			}
			
		}
		
	}
	
	public static void render(Player p) {
		for (BaseActor render : renders) {
			if (render.distanceTo(p.center.x, p.center.y) < renderDist) {
				render.setVisible(true);
			} else {
				render.setVisible(false);
			}
		}
	}
	
}
