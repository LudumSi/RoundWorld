package com.ue.roundworld.client;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

	
	
	
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
		
		
		HashMap<String, String> args = new HashMap<String, String>();
		start = data.indexOf("{");
		id = data.substring(0, start);
		data = data.substring(start+1);
		while (!data.equals("}")) {
			String[] arg = parseArg(data);
			args.put(arg[0], arg[1]);
			if (data.contains(",")) {
				data = data.substring(data.indexOf(",") + 1);
			} else {
				data = "}";
			}
			//substring away the arg
			
		}
		data = data.substring(0, data.length()-1);
		Event e = new Event(id, args);
		
		return e;
	}
	
	private static String[] parseArg(String data) {
		if (data.contains(",")) {
			String arg = data.substring(0, data.indexOf(","));
			return arg.split("=");
		} else {
			String arg = data.substring(0, data.indexOf("}"));
			return arg.split("=");
		}
		
	}
}
