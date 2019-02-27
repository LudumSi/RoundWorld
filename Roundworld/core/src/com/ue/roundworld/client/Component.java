package com.ue.roundworld.client;

import java.util.ArrayList;

public class Component {
	
	public static enum Type{
		text;
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
			out += this.args.get(i) + ",";
		}
		out += ")";
		return out;
	}
	
	public String getArg(String key) {
		for (int i = 0; i < args.size(); i++) {
			
			String[] splitArg = args.get(i).split("\\|");
			if (splitArg[0].equals(key)) {
				return splitArg[1];
			}
		}
		return null;
	}
	
	public static String generate(Component.Type t, String...args) {
		String str = "";
		switch(t) {
		case text:
			str += "(0:";
			for (int i = 0; i < args.length; i++) {
				str += args[i];
			}
			str += ")";
			break;
		
		}
		return str;
	}
}
