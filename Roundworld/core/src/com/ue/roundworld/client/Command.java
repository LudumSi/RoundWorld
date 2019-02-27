package com.ue.roundworld.client;

import java.util.ArrayList;

public class Command {
	
	
	
	public static enum Type{
		sendText;
	}
	
	private int id;
	ArrayList<Component> components = new ArrayList<Component>();
	public Command(int id, ArrayList<Component> components) {
		this.id = id;
		this.components = components;
	}
	
	public String toString() {
		String out =  Integer.toHexString(id) + "{";
		for (int i = 0; i < this.components.size(); i++) {
			out += this.components.get(i).toString();
		}
		out += "}";
		return out;
	}
	
	public int get_id() {
		return id;
	}
	
	public Component get_component(int id) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).id == id) {
				return components.get(i);
			}
		}
		return null;
	}
	
	public static String generate(Command.Type t, String...c) {
		String str = "";
		switch (t) {
		case sendText:
			str += "1{";
			for (int i = 0; i < c.length; i++) {
				str += c[i];
			}
			str += "}";
			break;
		}
		return str;
	}
}
