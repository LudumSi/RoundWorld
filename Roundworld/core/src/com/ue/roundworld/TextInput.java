package com.ue.roundworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ue.roundworld.client.Client;
import com.ue.roundworld.client.Command;
import com.ue.roundworld.client.Component;

public class TextInput extends BaseActor implements InputProcessor {
	
	
	String text = "";
	Label[] log = new Label[11];
	Client c;
	String lastText = "";
	String lastAdd = "";
	private boolean hideLog;
	public TextInput(int x, int y, Client c) {
		super(Utils.emptyTexture);
		this.setPosition(x, y);
		this.c = c;
		
		for (int i = 0; i < log.length; i++) {
			Label l = new Label(" ", RoundWorld.font);
			l.setPosition(0, x + 160 - i * 16);
			this.addActor(l);
			log[i] = l;
		}
	}
	
	public void add_to_log(String name, String text, Color c) {
		for (int i = 0; i < log.length-2; i++) {
			log[i].setText(log[i+1].getText());
			log[i].setColor(log[i+1].getColor());
		}
		log[9].setText(name + ": " + text);
		log[9].setColor(c);
	}
	
	public String getLastAdd() {
		return lastAdd;
		
	}
	
	public void setHideLog(boolean b) {
		if (b) {
			for (int i = 0; i < log.length-2; i++) {
				log[i].remove();
			}
		} else {
			for (int i = 0; i < log.length-2; i++) {
				this.addActor(log[i]);
			}
		}
		hideLog = b;
	}

	public void update() {
		Command com = c.getParsedData();
	
		if( !this.hideLog && com!= null && com.get_id() == 0xC001 && com.get_component(0, 0) != null && com.get_component(0, 1) != null) {
			
				if (
					!lastText.equals(com.get_component(0, 1).getArg("text"))) {
					lastText = com.get_component(0, 1).getArg("text");
					add_to_log( com.get_component(0, 0).getArg("text"), com.get_component(0, 1).getArg("text"), Color.WHITE);
					
				}
		}
	}
	
	public String getKeyVal(int keycode, boolean shifted) {
		switch (keycode) {
		// META* variables should not be used with this method.
		case Keys.UNKNOWN:
			return "Unknown";
		case Keys.NUM_0:
			if (shifted) return ")";
			return "0";
		case Keys.NUM_1:
			if (shifted) return "!";
			return "1";
		case Keys.NUM_2:
			if (shifted) return "@";
			return "2";
		case Keys.NUM_3:
			if (shifted) return "#";
			return "3";
		case Keys.NUM_4:
			if (shifted) return "$";
			return "4";
		case Keys.NUM_5:
			if (shifted) return "%";
			return "5";
		case Keys.NUM_6:
			if (shifted) return "^";
			return "6";
		case Keys.NUM_7:
			if (shifted) return "&";
			return "7";
		case Keys.NUM_8:
			if (shifted) return "*";
			return "8";
		case Keys.NUM_9:
			if (shifted) return "(";
			return "9";
		case Keys.A:
			if (shifted) return "A";
			return "a";
		case Keys.B:
			if (shifted) return "B";
			return "b";
		case Keys.C:
			if (shifted) return "C";
			return "c";
		case Keys.D:
			if (shifted) return "D";
			return "d";
		case Keys.E:
			if (shifted) return "E";
			return "e";
		case Keys.F:
			if (shifted) return "F";
			return "f";
		case Keys.G:
			if (shifted) return "G";
			return "g";
		case Keys.H:
			if (shifted) return "H";
			return "h";
		case Keys.I:
			if (shifted) return "I";
			return "i";
		case Keys.J:
			if (shifted) return "J";
			return "j";
		case Keys.K:
			if (shifted) return "K";
			return "k";
		case Keys.L:
			if (shifted) return "L";
			return "l";
		case Keys.M:
			if (shifted) return "M";
			return "m";
		case Keys.N:
			if (shifted) return "N";
			return "n";
		case Keys.O:
			if (shifted) return "O";
			return "o";
		case Keys.P:
			if (shifted) return "P";
			return "p";
		case Keys.Q:
			if (shifted) return "Q";
			return "q";
		case Keys.R:
			if (shifted) return "R";
			return "r";
		case Keys.S:
			if (shifted) return "S";
			return "s";
		case Keys.T:
			if (shifted) return "T";
			return "t";
		case Keys.U:
			if (shifted) return "U";
			return "u";
		case Keys.V:
			if (shifted) return "V";
			return "v";
		case Keys.W:
			if (shifted) return "W";
			return "w";
		case Keys.X:
			if (shifted) return "X";
			return "x";
		case Keys.Y:
			if (shifted) return "Y";
			return "y";
		case Keys.Z:
			if (shifted) return "Z";
			return "z";
		case Keys.COMMA:
			if (shifted) return "<";
			return ",";
		case Keys.PERIOD:
			if (shifted) return ">";
			return ".";
		case Keys.ALT_LEFT:
			return "ALT";
		case Keys.ALT_RIGHT:
			return "ALT";
		case Keys.TAB:
			return "\t";
		case Keys.SPACE:
			return " ";
		case Keys.DEL:
			return "\b"; // also BACKSPACE
		case Keys.GRAVE:
			if (shifted) return "~";
			return "`";
		case Keys.MINUS:
			if (shifted) return "_";
			return "-";
		case Keys.EQUALS:
			if (shifted) return "+";
			return "=";
		case Keys.LEFT_BRACKET:
			if (shifted) return "{";
			return "[";
		case Keys.RIGHT_BRACKET:
			if (shifted) return "}";
			return "]";
		case Keys.BACKSLASH:
			if (shifted) return "|";
			return "\\";
		case Keys.SEMICOLON:
			if (shifted) return ":";
			return ";";
		case Keys.APOSTROPHE:
			if (shifted) return "\"";
			return "'";
		case Keys.SLASH:
			if (shifted) return "?";
			return "/";
		case Keys.FORWARD_DEL:
			return "\b";
		case Keys.CONTROL_LEFT:
			return "CTRL";
		case Keys.CONTROL_RIGHT:
			return "CTRL";
		case Keys.ESCAPE:
			return "ESC";
		case Keys.INSERT:
			return "INS";
		case Keys.NUMPAD_0:
			return "0";
		case Keys.NUMPAD_1:
			return "1";
		case Keys.NUMPAD_2:
			return "2";
		case Keys.NUMPAD_3:
			return "3";
		case Keys.NUMPAD_4:
			return "4";
		case Keys.NUMPAD_5:
			return "5";
		case Keys.NUMPAD_6:
			return "6";
		case Keys.NUMPAD_7:
			return "7";
		case Keys.NUMPAD_8:
			return "8";
		case Keys.NUMPAD_9:
			return "9";
		default:
			// key name not found
			return null;
		}
		
	}
	
	
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode != Keys.ENTER && keycode != Keys.BACKSPACE) {
			String key = getKeyVal(keycode, Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT));
			if (key != null) {
				text += key;
				log[10].setText(text);
			}
		
		} else if (keycode == Keys.BACKSPACE) {
			if (text.length() > 0) {
				char[] arr = text.toCharArray();
				char[] newArr = new char[text.length()-1];
				for (int i = 0; i < text.length()-1; i++) {
					newArr[i] = arr[i];
				}
				text = String.valueOf(newArr);
				log[10].setText(text);
			}
			
			
		}
		else {
			this.lastAdd = log[10].getText().toString();
			if (!this.hideLog) {
				add_to_log(Client.user, text, Color.WHITE);
				c.sendRequest(Command.generate(
						Command.Type.sendText, 
						Component.generate(Component.Type.text, "text|" + Client.user),
						Component.generate(Component.Type.text, "text|" + text)
						));
			}
		
			text = "";
			log[10].setText(text);
			
		}
		
		return false;
	}
	
	


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}