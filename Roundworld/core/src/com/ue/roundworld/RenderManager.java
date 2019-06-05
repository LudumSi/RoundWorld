package com.ue.roundworld;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonValue;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class RenderManager {
	
	private static ArrayList<BaseActor> groundLayer = new ArrayList<BaseActor>();
	private static ArrayList<BaseActor> surfaceLayer = new ArrayList<BaseActor>();
	private static ArrayList<BaseActor> roofLayer = new ArrayList<BaseActor>();
	
	private static ArrayList<Player> localPlayers = new ArrayList<Player>();
	
	private static final int renderDist = 1000;
	
	
	public static void getRenders(String area, Stage m) {
		//request renders
		Event e = new Event("render_request");
		e.addArg("area", area);
		
		Client.sendRequest(e.generate());
		
		//clear layers
		groundLayer.clear();
		surfaceLayer.clear();
		roofLayer.clear();
		
		localPlayers.clear();
		
		boolean done = false;
		int reqAttempts = 0;
	
		while(!done)  {
			//get and verify data
			reqAttempts++;
			Event ev = Client.popParsedData();
			System.out.println(ev); //REMOVING THIS BREAKS EVERYTHING
			if (Event.verify(ev, "render_list")) {
					//load ground layer
					System.out.println("parsing renders...");
					JsonValue[] ground = ev.getArray("ground");
					
					for (int i = 0; i < ground.length; i++) {
						//create base actors and add to ground layer.
						BaseActor b = new BaseActor(AssetManager.getTexture(ground[i].getString("texture")));
						b.setPosition(ground[i].getInt("x"), ground[i].getInt("y"));
						m.addActor(b);
						groundLayer.add(b);
					}
					/*//load surface layer
					JsonValue[] surface = ev.getArray("surface");
					
					for (int i = 0; i < surface.length; i++) {
						//create base actors and add to ground layer.
						BaseActor b = new BaseActor(AssetManager.getTexture(surface[i].getString("texture")));
						b.setPosition(surface[i].getInt("x"), surface[i].getInt("y"));
						m.addActor(b);
						surfaceLayer.add(b);
					}
					//load roof layer
					JsonValue[] roof = ev.getArray("roof");
					
					for (int i = 0; i < roof.length; i++) {
						//create base actors and add to ground layer.
						BaseActor b = new BaseActor(AssetManager.getTexture(roof[i].getString("texture")));
						b.setPosition(roof[i].getInt("x"), roof[i].getInt("y"));
						m.addActor(b);
						surfaceLayer.add(b);
					}*/
					
					
					//get players
					
					/*JsonValue[] players = ev.getArray("players");
					for (int i = 0; i < players.length; i++) {
						Player p = null;
						while(p == null) {
							p = spawnPlayer(players[i].getString("name"), false);
						}
						m.addActor(p);
						localPlayers.add(p);
					}*/
					
					done = true;
			}
		
				
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
		
		for (Player render : localPlayers) {
			if (render.distanceTo(p.center.x, p.center.y) < renderDist) {
				render.setVisible(true);
			} else {
				render.setVisible(false);
			}
		}
		
	}
	
	/**
	 * sends an event to the server to spawn in a player with name name 
	 * @param name the name of the player to spawn in
	 * @return the player with name name
	 */
	public static Player spawnPlayer(String name, boolean isClient) {
		Event e = new Event("spawn_player");
		e.addArg("name", name);
		if (isClient) {
			e.addArg("isClient", 1);
		} else {
			e.addArg("isClient", 0);
		}
		Client.sendRequest(e.generate());
		Player p = null;
		
		Event ev = Client.getParsedData();
		if (Event.verify(ev, "re|spawn_player")) {
			Client.popParsedData();
			p = new Player();
			if (ev.getInt("success") == 1) {
				//Success! load data into player
				if (ev.getInt("isClient") == 1) {
					p.setIsClient(true);
				}
				
				p.setName(ev.getString("name"));
				p.setNameColor( Color.valueOf(ev.getString("name_color")));
			
				p.setCenter(ev.getFloat("x"), ev.getFloat("y"));
				
			} else {
				//failure! print error or start up character creation!
				if (e.getInt("isClient") == 1) {
					p.setIsClient(false);
					//TODO character creation flag here
					return null;
				} else {
					System.out.println("ERROR: player \"" + name  + "\" does not exist");
				}
			}
			
		}
		
		
		return p;
		
	}
	
}
