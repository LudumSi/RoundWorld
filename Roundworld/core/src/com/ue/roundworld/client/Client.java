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

	public static String user;
	
	public boolean isConnected = false;

	private String receivedData = "NONE";
	private Queue<String> dataQueue = new Queue<String>();
	private String connectionResult = "";
	private String ip;
	private int port;
	

	
	private Socket socket; 

	

	/**
	 * Creates a severClient connection to the given ip; this will only throw
	 * errors when calling sendRequest if the ip can't be found
	 * 
	 * @param ip the ip to connect to
	 */
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
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
		this.receive.start();

	}

	/**
	 * sends data to the server
	 * 
	 * @param data the data to be sent
	 * @param com the command for the server to perform
	 */
	public void sendRequest(String jsonStringToSend) {

		try {
			// write our entered message to the stream
			System.out.println("Sent: " + jsonStringToSend);
			socket.getOutputStream().write(jsonStringToSend.getBytes());
		} catch (Exception e) {
			System.out.println("Socket write error");

		}

	}

	private Thread receive = new Thread(new Runnable() {

		@Override
		public void run() {

			String data = "";
			String prevData = "";
			while (true) {
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
	public String getRecievedData() {

		return this.receivedData;
	}

	public Command getParsedData() {
		if (isCommandComplete()) {
			return Parser.parse(dataQueue.removeFirst());
		} else {
			return null;
		}
		
	}
	
	public void close() {
		this.sendRequest("FFFF{(0:text|bye)}");
		receivedData = "STOP";
		try {
			receive.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.dispose();
	
		
	}

	
	public boolean isCommandComplete() {
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
					return true;
				} else if (dataQueue.size > 0) {
					begin += dataQueue.removeFirst();
				}
			}
			dataQueue.addFirst(begin);
		}
		
		return false;
		
		
		
	}
}
