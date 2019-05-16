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
	
	/**
	 * gets a component in this command
	 * @param id the component type
	 * @param num the index of the component of type id, 0 is first, 1 is second, etc...
	 * @return the component
	 */
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
	
	/**
	 * generates a command
	 * @param t the type of the command
	 * @param c the string components of the command
	 * @return a string representing the command
	 */
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
	
	/**
	 * makes sure the given command exists and has the correct number of components
	 * @param com the command to check
	 * @param type the expected component type
	 * @param expectedComponents the number of expected components
	 * @return whether to command is valid 
	 */
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
