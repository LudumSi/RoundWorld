package com.ue.roundworld.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.ue.roundworld.Utils;

public class Event {
	
	
	private String id;
	private HashMap<String, String> args = new HashMap<String, String>();
	
	public Event(String id, HashMap<String, String> args) {
		this.id = id;
		this.args = args;
	}
	
	public Event(String id) {
		this.id = id;

	}
	
	public void addArg(String key, String val) {
		this.args.put(key, val);
	}
	
	
	public String toString() {
		return generate();
	}
	
	public String getId() {
		return id;
	}
	
	public int getNumArgs() {
		return args.size();
	}
	
	/**
	 * gets a component in this command
	 * @param id the component type
	 * @param num the index of the component of type id, 0 is first, 1 is second, etc...
	 * @return the component
	 */
	public String getArg(String arg) {
		return args.get(arg);
	}
	
	/**
	 * generates a command
	 * @param t the type of the command
	 * @param c the string components of the command
	 * @return a string representing the command
	 */
	public String generate() {
		String str = "";
		str += this.id + "{";
		str += this.args.toString();
		str += "}";
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
	public static boolean verify(Event e) {
		if (e == null) {
			return false;
		}
		
		return true;
	}
}
