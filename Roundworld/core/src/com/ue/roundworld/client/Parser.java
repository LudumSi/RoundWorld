package com.ue.roundworld.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.JsonReader;

public class Parser {

	
	private static JsonReader reader = new JsonReader();
	
	/**
	 * converts a command string into a command
	 * @param data the command string
	 * @return a Command representing the command string
	 */
	public static Event parse(String data) {
		int start = 0;
		
		String id = "";
		int len = 0;
		
		/*getLength*/
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '|') {
				len = Integer.parseInt(data.substring(0, i));
				start = i + 1;
				break;
			}
			
		}
		//read the rest of the data
		data = data.substring(start, len);
	
		Event e = new Event();
		
		e.setJson(reader.parse(data));
		return e;
	}

}
