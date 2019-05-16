package com.ue.roundworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;

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
		Command com = Client.getParsedData();
	
		if(Command.verify(com, Component.Type.velocity, 1) && Command.verify(com, Component.Type.position, 1) && com.get_id() == 0xC002) {
			Client.popParsedData();
			if (id == Integer.parseInt(com.get_component(Component.Type.value, 0).getArg(0))){
				this.setCenter(Float.parseFloat(com.get_component(Component.Type.position, 0).getArg(0)), 
						Float.parseFloat(com.get_component(Component.Type.position, 0).getArg(1)));
				this.setVelX(Float.parseFloat(com.get_component(Component.Type.velocity, 0).getArg(0)));
				this.setVelY(Float.parseFloat(com.get_component(Component.Type.velocity, 0).getArg(1)));
				
			}
				
				
			
		}
		super.act(dt);
	}
	
	/**
	 * sends a position and velocity update to the server
	 */
	public void sendVelUpdate() {
		if (Client.isConnected()) {
			Client.sendRequest(
					Command.generate(Command.Type.velUpdate, 
							Component.generate(Component.Type.value, Integer.toString(0)),
							Component.generate(Component.Type.position, Float.toString(this.center.x), Float.toString(this.center.y)),
							Component.generate(Component.Type.velocity, Float.toString(vel.x), Float.toString(vel.y)
									)
							
							)
					
					);
			
		
		}
	}
	
	
}
