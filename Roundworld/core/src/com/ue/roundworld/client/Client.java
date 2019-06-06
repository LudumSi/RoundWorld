package com.ue.roundworld.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.Queue;
import com.ue.roundworld.Player;


public class Client {

	public static String userIpAddress;

	public static String user = "player1";
	
	private static boolean isConnected = false;
	private static boolean isRunning = true;

	private static String receivedData = "NONE";
	private static Queue<String> dataQueue = new Queue<String>();

	private static String ip;
	private static int port;

	public static Player player;
	
	private static Socket socket; 

	

	/**
	 * Creates a severClient connection to the given ip; this will only throw
	 * errors when calling sendRequest if the ip can't be found
	 * 
	 * @param ip the ip to connect to
	 * @param port the port
	 */
	public static void init(String ips, int ports) {
		ip = ips;
		port = ports;
		SocketHints socketHints = new SocketHints();
		// Socket will time our in 4 seconds
		socketHints.connectTimeout = 4000;
		// create the socket and connect to the server entered in the text box (
		// x.x.x.x format ) on port 9021
	
		System.out.println("connecting to: " + ip);
	
		socket = Gdx.net.newClientSocket(Protocol.TCP, ip, port, socketHints);
		isConnected = true;
	
			
		
		
		System.out.println("connected to: " + ip);
		isConnected = true;
		receive.start();

	}

	/**
	 * sends data to the server
	 * 
	 * @param s the data to be sent
	 * 
	 */
	public static void sendRequest(String s) {
		if (isConnected) {
			try {
				// write our entered message to the stream
				System.out.println("Sent: " + s);
				socket.getOutputStream().write(s.getBytes());
			} catch (Exception e) {
				System.out.println("Socket write error " + e.getMessage());
				isConnected = false;
			}
		}

	}

	private static Thread receive = new Thread(new Runnable() {

		@Override
		public void run() {

			String data = "";
			String prevData = "";
			isRunning = true;
			while (isRunning) {
			
				BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				try {
					
					if (buffer.ready()) {
						
						data = buffer.readLine();
						

					}
				} catch (IOException e) {
					
					System.out.println(e.toString());
					data = "error";
				}
				
				if (!data.equals(prevData)) {
					System.out.println("Recieved: " + data);
					
					receivedData = data;
					dataQueue.addLast(data);
					if (receivedData.equals("STOP")) {
						break;
					}
				

				}
				prevData = data;
				
			}

		}

	});

	/**
	 * gets the data received from the server, may be an empty string
	 * 
	 * @return String the data recieve from the server
	 */
	public static String getRecievedData() {

		return receivedData;
	}
	
	/**
	 * removes the command at the front of the data queue
	 * @return the command at the front of the data queue
	 */
	public static Event popParsedData() {
		/*add search for correct command*/
		
			if (dataQueue.size> 0) {
				return Parser.parse(dataQueue.removeFirst());
			}
			return null;
		
		
		
	}
	
	/**
	 * snags parsed data from the data queue
	 * @return A command representing the first command in the data queue
	 */
	public static Event getParsedData() {
		//if (isCommandComplete()) {
			if (dataQueue.size> 0) {
				
				return Parser.parse(dataQueue.first());
			}
			return null;
		
		//} else {
			//return null;
		//}
		
	}
	
	/**
	 * stops the server
	 */
	public static void close() {
		sendRequest("FFFF{(0:text|bye)}");
		receivedData = "STOP";
		isRunning = false;
		try {
			receive.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.dispose();
	
		
	}

	/**
	 * checks to see if the command at the front of the data queue
	 * is complete
	 * @return if the command is complete or not
	 */
	public static boolean isCommandComplete() {
		if (dataQueue.size > 0) {
			String begin = dataQueue.removeFirst();
			String length = "";
			int len = 0;
			/*get length*/
			for (int i = 0; i < begin.length(); i++) {
				if (begin.charAt(i) == 'L') {
					length = begin.substring(0, i);
					break;
				}
			}
			if (length.length() > 0) {
				
				len = Integer.parseInt(length);
			}
			
			
			/*check size*/
			for (int i = 0; i < dataQueue.size; i++) {
				if (begin.length() == len) {
					dataQueue.addFirst(begin);
					System.out.println("GOOD: " + dataQueue.first());
					return true;
				} else if (begin.length() <= len) {
					begin += dataQueue.removeFirst();
					System.out.println("BAD! Should be: " + Integer.toString(len) + " is: " +  Integer.toString(begin.length()));
					return false;
				} else if (begin.length() >= len) {
					dataQueue.addFirst(begin.substring(0, len));
					System.out.println("GOOD: "+ dataQueue.first());
					return true;
				}
			}
			dataQueue.addFirst(begin);
		
		}
		
		return false;
		
		
		
		
	}
	
	public static boolean isConnected() {
		return isConnected;
	}

	public static void clear() {
		dataQueue.clear();
		
	}
}
