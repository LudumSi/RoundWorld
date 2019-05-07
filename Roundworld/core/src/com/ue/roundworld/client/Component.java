package com.ue.roundworld.client;

import java.util.ArrayList;

public class Component {
	
	public static enum Type{
		text, value, velocity, position, render;
		
		public int id;
		
		static {
			text.id = 0x0;
			value.id = 0x1;
			velocity.id = 0x2;
			position.id = 0x3;
			render.id = 0x4;
			
		}
	}
	
	
	
	int id;
	ArrayList<String> args = new ArrayList<String>();
	public Component(int id, ArrayList<String> args) {
		this.id = id;
		this.args = args;
	}
	
	public String toString() {
		String out = "(" + Integer.toHexString(id) + ":";
		for (int i = 0; i < this.args.size(); i++) {
			out += this.args.get(i);
		}
		out += ")";
		return out;
	}
	
	public String getArg(int key) {

			
		return args.get(key).substring(0, args.get(key).length()-1);
	}
	
	public static String generate(Component.Type t, String...args) {
		String str = "";
		str += "(" + Integer.toHexString(t.id).toUpperCase() +":";
		for (int i = 0; i < args.length; i++) {
			str += args[i] + ",";
		}
		str += ")";
		return str;
	}
}
