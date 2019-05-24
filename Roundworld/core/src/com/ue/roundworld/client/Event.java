package com.ue.roundworld.client;


import java.util.HashMap;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.ue.roundworld.Utils;

public class Event{

	
	private String id;
	private JsonValue json;
	public Event(String id, HashMap<String, String> args) {
		json = new JsonValue(ValueType.object);
		json.setName(id);
		this.id = id;
	}
	
	public Event(String id) {
		json = new JsonValue(ValueType.object);
		json.setName(id);
		this.id = id;

	}
	
	public Event() {
		this.json = null;
	}
	
	public void load(String json) {
		JsonReader r = new JsonReader();
		this.json = r.parse(json);
		this.id = this.json.name();
	}
	
	public void addArg(String key, String val) {
		json.addChild(key, new JsonValue(val));
	}
	public void addArg(String key, float val) {
		json.addChild(key, new JsonValue(val));
	}
	public void addArg(String key, int val) {
		json.addChild(key, new JsonValue(val));
	}
	
	public String getString(String key) {
		return json.getString(key);
	}
	public int getInt(String key) {
		return json.getInt(key);
	}
	public float getFloat(String key) {
		return json.getFloat(key);
	}
	
	
	public String toString() {
		return super.toString();
	}
	
	public String getId() {
		return id;
	}
	
	
	public JsonValue[] getArray(String ar) {
		JsonValue b = json.get(ar).child;
		JsonValue[] arr = new JsonValue[b.size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = b.next();
			b = b.next();
		}
		return arr;
	}
	
	/**
	 * generates the event
	 * @return a string representing the event
	 */
	public String generate() {
		/*String str = "";
		str += this.id;
		str += Utils.removeSpaces(this.args.toString());
		int len = 0;
		len = str.length();
		len += Utils.getDigits(len);
		len++;
		str = Integer.toString(len) + "|" + str + "\n";*/
		String str = "";
		System.out.println("json: " + json.toJson(OutputType.minimal));
		return json.toJson(OutputType.minimal);
		
		
		
		
		
	}
	
	/**
	 * makes sure the given command exists and has the correct number of components
	 * @param com the command to check
	 * @param type the expected component type
	 * @param expectedComponents the number of expected components
	 * @return whether to command is valid 
	 */
	public static boolean verify(Event e, String id) {
		return e != null && e.id.equals(id);
	}
}
