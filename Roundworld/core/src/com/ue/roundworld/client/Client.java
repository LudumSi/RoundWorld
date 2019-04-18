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


public class Client {

	public static String userIpAddress;

	public static String user = "ME";
	
	private static boolean isConnected = false;
	private static boolean isRunning = true;

	private static String receivedData = "NONE";
	private static Queue<String> dataQueue = new Queue<String>();

	private static String ip;
	private static int port;

		
	
	private static Socket socket; 

	

	/**
	 * Creates a severClient connection to the given ip; this will only throw
	 * errors when calling sendRequest if the ip can't be found
	 * 
	 * @param ip the ip to connect to
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
	 * @param data the data to be sent
	 * @param com the command for the server to perform
	 */
	public static void sendRequest(String jsonStringToSend) {

		try {
			// write our entered message to the stream
			System.out.println("Sent: " + jsonStringToSend);
			socket.getOutputStream().write(jsonStringToSend.getBytes());
		} catch (Exception e) {
			System.out.println("Socket write error " + e.getMessage());
			isConnected = false;
		}

	}

	private static Thread receive = new Thread(new Runnable() {

		@Override
		public void run() {

			String data = "";
			String prevData = "";
			isRunning = true;
			while (isRunning) {
				if (receivedData.equals("STOP")) {
					break;
				}
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
					if (receivedData.equals("STOP")) {
						break;
					}
					receivedData = data;
					dataQueue.addLast(data);
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

	public static Command getParsedData() {
		//if (isCommandComplete()) {
			if (dataQueue.size> 0) {
				return Parser.parse(dataQueue.removeFirst());
			}
			return null;
		
		//} else {
			//return null;
		//}
		
	}
	
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
					System.out.println("GOOD!");
					return true;
				} else if (dataQueue.size > 0) {
					begin += dataQueue.removeFirst();
					System.out.println("BAD! Should be: " + Integer.toString(len) + " is: " +  Integer.toString(begin.length()));
				}
			}
			dataQueue.addFirst(begin);
		
		}
		
		return false;
		
		
		
		
	}
	
	public static boolean isConnected() {
		return isConnected;
	}
}
