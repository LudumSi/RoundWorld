package com.ue.roundworld.client;

import java.util.ArrayList;

import com.ue.roundworld.Utils;

public class Command {
	
	
	
	public static enum Type{
		initConnect, sendText, velUpdate;
		
		int id;
		
		static {
			initConnect.id = 0xC000;
			sendText.id = 0xC001;
			velUpdate.id = 0xC002;
		}
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
	
	public int getNumComponents() {
		return components.size();
	}
	
	public Component get_component(Component.Type id, int num) {
		int count = 0;
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).id == id.id) {
				if (count == num) {
					return components.get(i);
				}
				count ++;
				
			}
		}
		return null;
	}
	
	public static String generate(Command.Type t, String...c) {
		String str = "";
		str += Integer.toHexString(t.id) + "{";
		for (int i = 0; i < c.length; i++) {
			str += c[i];
		}
		str += "}\n";
		int len = 0;
		len = str.length();
		len += Utils.getDigits(len);
		str = Integer.toString(len) + "L" + str;
		return str;
	}
	
	public static boolean verify(Command com, Component.Type type, int expectedComponents) {
		if (com == null) {
			return false;
		}
		
		for (int i = 0; i < expectedComponents; i++) {
			if (com.get_component(type, i) == null) {
				return false;
			}
		}
		return true;
	}
}
