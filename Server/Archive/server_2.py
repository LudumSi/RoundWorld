import structure
import socket
import threading
import sys
import time
#from parser import parse

#--Gotta add message length to command header
#--use global locked queue to send things between threads (included in python)
#--see: async io 

class client_thread(shittyPrestonThread):

	def run():

	def disconnect():


class connecting_thread():

	def __init__(self):
		
		print("Initializing")
		
		super().__init__()
		
		try: 
    		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
    		print "Socket successfully created"
		except socket.error as err: 
    		print "socket creation failed with error %s" %(err)
		
		hostname = socket.gethostname() #Some black magic fuckery to get the local IP
		
		HOST = socket.gethostbyname(hostname)
		PORT = 1337

		self.server.bind((HOST,PORT))
		self.server.settimeout(20)
		
	def run(self):
		
		global running
		
		print("Running")
		
		#--parameter is backlog of connections in queue. This may have to be modified later
		self.server.listen(1)
		
		while(running):
			

			
			try:
				#--Lots of things can go wrong here: read/write timeouts, heartbeats, external idle kills etc...
				conn,address = self.server.accept()
				
				self.queueArray(clients)
				
				#Important code to make sure the client doesn't connect multiple times
				new_connection = True
				
				for client in clients.array:
					
					if client.address[0] == address[0]:
						
						new_connection = False
					
				if new_connection:
					
					new_client = clientThread(conn,address)
					new_client.daemon = True
					clients.array.append(new_client)
					new_client.start()
					
					print("Connected to " + address[0])
					print(f"Current players: {len(clients.array)}")

					print(f"Clients: {clients.array}")
					#message_to_send = "Hello Beautiful!\n".encode("UTF-8")
					#new_client.connection.send(message_to_send)
						
				clients.release()
				
				#Create a new client, automatically adding it to the clients list
			
			except Exception as e:
				print(e)
				
		self.server.shutdown(socket.SHUT_RDWR)
		self.server.close()
		print("Closed listener")


class game_thread():


while running:
