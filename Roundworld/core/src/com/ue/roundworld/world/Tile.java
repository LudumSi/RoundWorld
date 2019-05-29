package com.ue.roundworld.world;

import com.ue.roundworld.AssetManager;
import com.ue.roundworld.BaseActor;
import com.ue.roundworld.Entity;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class Tile extends BaseActor{
	
	int id;
	boolean isSolid;
	boolean interactable;
	boolean contactable;
	public Tile(String texture, int id, boolean isSolid, boolean interactable, boolean contactable) {
		super(AssetManager.getTexture(texture));
		this.isSolid = isSolid;
		this.interactable = interactable;
		this.contactable = contactable;
		this.id = id;
	}
	
	
	public void interact(Entity e) {
		if (interactable) {
			Event ev = new Event("interact");
			ev.addArg("tile", id);
			ev.addArg("entity_id", e.getId());
			Client.sendRequest(ev.generate());
			//only sends the request, the response is handled elsewhere
		}
	}
	
	public void onContact(Entity e) {
		if (contactable) {
			Event ev = new Event("contact");
			ev.addArg("tile", id);
			ev.addArg("entity_id", e.getId());
			Client.sendRequest(ev.generate());
			//only sends the request, the response is handled elsewhere
		}
		
		if (isSolid) {
			//moves entity out of this
			e.overlaps(this, true);
			
		}
	}
}
