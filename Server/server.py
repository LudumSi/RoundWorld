
import structure
import socket
import threading
import sys
import parser

server_data = []
clients = []
threads = []

running = True

#--Gotta add message length to command header
#--use global locked queue to send things between threads (included in python)
#--see: async io 

#Client thread class
#--Python's threads don't run concurrently.
class clientThread(threading.Thread):

	def __init__(self,conn,address):
		
		threading.Thread.__init__(self)
		
		self.connection = conn
		self.address = address
		
		#--Don't do this, shared mutable data like this can become easily corrupted
		clients.append(self)
		threads.append(self)
		
	def run(self):
		
		global running
		
		print(f"Created client for {self.address}")
		
		while(running):
			
			#--Arg is bytes recieved, doesn't always work bc of data rerouteing and other stuff.
			try:
				data = self.connection.recv(1024)
				#parser.parse(data) Doesn't return anything, so why is it here?
			#print("Server recieved: %s" % data)
				server_data.append((self, data))
			
			except:
				
				pass
				
	def disconnect(self):
		
		print(f"Disconnecting from {self.address}!")
		
		print(f"Clients: {clients}")
		
		print(f"Removing {self} from clients")
		
		clients.pop(clients.index(self))
		
		print("Removed from clients")
		
		self.connection.close()
		
		print("Closing connection")

	#Connecting thread class
class connectingThread(threading.Thread):

	def __init__(self,threadID):
		
		print("Initializing")
		
		threading.Thread.__init__(self)
		self.threadID = threadID
		
		self.server = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
		
		hostname = socket.gethostname() #Some black magic fuckery to get the local IP
		
		HOST = socket.gethostbyname(hostname)
		PORT = 1337

		self.server.bind((HOST,PORT))
		self.server.settimeout(20)
		
		threads.append(self)
		
	def run(self):
		
		global running
		
		print("Running")
		
		#--parameter is backlog of connections in queue. This may have to be modified later
		self.server.listen(1)
		
		while(running):
			
			try:
				#--Lots of things can go wrong here: read/write timeouts, heartbeats, external idle kills etc...
				conn,address = self.server.accept()
				
				for client in clients:
					
					if client.connection != conn:
						
						print("Connected to " + address[0])
						message_to_send = "Hello Beautiful!\n".encode("UTF-8")
						conn.send(message_to_send)
				
				#Create a new client, automatically adding it to the clients list
				new_client = clientThread(conn,address)
				new_client.daemon = True
				new_client.start()
				
				#Clears the connection and the address for the next loop
				conn = 0
				address = 0
			
			except Exception as e:
				print(e)
				
		conn.close()
		print("Closed listener")
		
class safetyThread(threading.Thread):
		
		def __init__(self):
			
			threading.Thread.__init__(self)
			
			threads.append(self)
		
		def run(self):
			
			global running
			
			while(running):
				
				user_input = input()
			
				if(user_input == "q"):
					running = False
			
		
#Multithreading. One thread is now always listening for new connections and client data

thread = connectingThread(1)
thread.daemon = True
thread.start()

safety = safetyThread()
safety.daemon = True
safety.start()

while(running):

	for index, data in enumerate(server_data):
		
		sending_client = data[0]
		data_sent = data[1]
		print(f"{sending_client.address}:{data_sent}")
		
		if data_sent == b'FFFF{(0:text|bye)}':
			
			if sending_client:
				
				sending_client.disconnect()
				#Makes sure the client is actually gone before disconnecting them. UNTESTED.
		
		else:
			
			for client in clients:
			
				if client != sending_client:
				
					message = data_sent
					client.connection.send(message) #Need to make sure people are online before sending them messages
			
		server_data.pop(index)

print("Shutting down")	
	
sys.exit()
		

		
		
		
		
	


	
	
	
	
	
