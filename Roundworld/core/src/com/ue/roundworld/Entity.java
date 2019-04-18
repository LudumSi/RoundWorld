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
	private int id;
	
	public Entity(Texture t) {
		super(t);
		id = globalID;
		globalID++;
		
		//remove
		id = 0;
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
		super.act(dt);
		this.moveBy(dt * 60 * vel.x, dt * 60 * vel.y);
		
		Command com = Client.getParsedData();
		if( Command.verify(com, Component.Type.velocity, 1) && com.get_id() == 0xC002) {
				if (this.id == Integer.parseInt(com.get_component(Component.Type.velocity.id, 0).getArg(0))) {
					this.setVelX(Float.parseFloat(com.get_component(Component.Type.velocity.id, 0).getArg(1)));
					this.setVelY(Float.parseFloat(com.get_component(Component.Type.velocity.id, 0).getArg(2)));
				}
			
		}
	}
	
	public void sendVelUpdate() {
		if (Client.isConnected()) {
			Client.sendRequest(
					Command.generate(Command.Type.velUpdate, 
							Component.generate(Component.Type.velocity, 
									Integer.toString(id), Float.toString(vel.x), Float.toString(vel.y)
									)
							
							)
					
					);
			
		
		}
	}
	
	
}
