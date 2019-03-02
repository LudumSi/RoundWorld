package com.ue.roundworld.client;

import java.util.ArrayList;

public class Parser {

	
	private static Component parseComp(String comp) {
		int start = 0;
		int compId = 0x0;
		ArrayList<String> args = new ArrayList<String>();
		for (int i = 0; i < comp.length(); i++) {
			if (comp.charAt(i) == ':') {
				compId = Integer.parseInt(comp.substring(start, i), 16);
				start = i + 1;
			}
			else if (comp.charAt(i) == ',' || i == comp.length()-1) {
				args.add(comp.substring(start, i + 1));
				start = i + 1;
			}
		}
		return new Component(compId, args);
	}
	
	public static Command parse(String data) {
		int start = 0;
		int commandName = 0;
		
		int len = 0;
		
		/*getLength*/
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == 'L') {
				len = Integer.parseInt(data.substring(0, i));
				start = i + 1;
				break;
			}
			
		}
		
		if (len != data.length()) {
			System.out.println("Bad Command Length: " + data);
			return null;
		}
		
		ArrayList<Component> comps = new ArrayList<Component>();
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '{') {
				commandName = Integer.parseInt(data.substring(start, i), 16);
				start = i + 1;
			}
			else if (data.charAt(i) == '(') {
				start = i + 1;
			}
			else if (data.charAt(i) == ')') {
				comps.add(parseComp(data.substring(start, i)));
				start = i + 1;
			}
		}
		return new Command(commandName, comps);
	}
}
