package com.ue.roundworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Event;

public class Entity extends BaseActor{

	private static int globalID = 0;
	
	private Vector2 vel = new Vector2();
	private Vector2 prevVel = new Vector2();
	private int id;
	
	public Entity(Texture t) {
		super(t);
		id = globalID;
	
		globalID++;
		
		id = 0;
		vel.x = 0;
		vel.y = 0;
		prevVel.x = 0;
		prevVel.y = 0;
	
	}
	
	public void addVel(float amtX, float amtY) {
		this.vel.x += amtX;
		this.vel.y += amtY;
		
	}
	public void setVelX(float amtX) {
		this.vel.x = amtX;
		
	}
	
	public void setVelY(float amtY) {
		this.vel.y = amtY;
		
	}
	public void act(float dt) {
		
		//check for vel update
		if (prevVel.x != vel.x || prevVel.y != vel.y) {
			prevVel.x = vel.x;
			prevVel.y = vel.y;
			sendVelUpdate();
			
		}
	
		this.moveBy(dt * 60 * vel.x, dt * 60 * vel.y);
		
		//update position and velocity from server
		Event e = Client.getParsedData();
	
		if(Event.verify(e, "velocity_update")) {
			Client.popParsedData();
			if (id == e.getInt("int")){
				this.setCenter(e.getFloat("x"), e.getFloat("y"));
				this.setVelX(e.getFloat("velocity_x"));
				this.setVelY(e.getFloat("velocity_y"));
				
			}
				
				
			
		}
		super.act(dt);
	}
	
	/**
	 * sends a position and velocity update to the server
	 */
	public void sendVelUpdate() {
		if (Client.isConnected()) {
			Event e = new Event("recvVelUpdate");
			e.addArg("id", Integer.toString(this.id));
			e.addArg("x", Float.toString(this.center.x));
			e.addArg("y", Float.toString(this.center.y));
			e.addArg("velocity_x", Float.toString(this.vel.x));
			e.addArg("velocity_y", Float.toString(this.vel.y));
			Client.sendRequest(e.generate());
			
		
		}
	}
	
	
}
