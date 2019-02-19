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


public class Client {

	public static String userIpAddress;

	public static String user;
	
	public boolean isConnected = false;

	private String receivedData = "NONE";
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
		while (!isConnected) {
			System.out.println("connecting to: " + ip);
			try {
				socket = Gdx.net.newClientSocket(Protocol.TCP, ip, port, socketHints);
				isConnected = true;
			} catch (Exception e) {
				System.out.println("failed connecting to: " + ip);
			}
			
		}
		
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
				BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				try {

					if (buffer.ready()) {
						data = buffer.readLine();

					}
				} catch (IOException e) {
					System.out.println("IO Exception");
					data = "error";
				}
				
				if (!data.equals(prevData)) {
					System.out.println("Recieved: " + data);
					receivedData = data;
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

	

}
