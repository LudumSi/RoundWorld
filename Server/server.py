
import structure
import socket
import threading

server_data = []
clients = []

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
		
	def run(self):
		
		print(f"Created client for {self.address}")
		
		while(1):
			#--Arg is bytes recieved, doesn't always work bc of data rerouteing and other stuff.
			data = self.connection.recv(1024)
			print("Server recieved: %s" % data)
			self.connection.send(data)

#Connecting thread class
class connectingThread(threading.Thread):

	def __init__(self,threadID):
		
		print("Initializing")
		
		threading.Thread.__init__(self)
		self.threadID = threadID
		
		self.server = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
		
		hostname = socket.gethostname() #Some black magic fuckery to get the local IP
		#
		HOST = "127.0.0.1"   #socket.gethostbyname(hostname)
		PORT = 1337

		self.server.bind((HOST,PORT))
		self.server.settimeout(20)
		
	def run(self):
		
		print("Running")
		
		#--parameter is backlog of connections in queue. This may have to be modified later
		self.server.listen(1)
		
		while(True):
			
			try:
				#--Lots of things can go wrong here: read/write timeouts, heartbeats, external idle kills etc...
				conn,address = self.server.accept()
				print("Connected to " + address[0])
				message_to_send = b"Hello Beautiful!\n"
				#--Gotta do some auth checks before sending stuff
				conn.send(message_to_send)
				
				#Create a new client, automatically adding it to the clients list
				new_client = clientThread(conn,address)
				new_client.start()
				
				#Clears the connection and the address for the next loop
				conn = NULL
				address = NULL
			
			except Exception as e:
				print(e)
				
		conn.close()
		

#Multithreading. One thread is now always listening for new connections and client data
		
thread = connectingThread(1)
thread.start()




	
	
	
	
	
