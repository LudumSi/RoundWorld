
import structure
import socket
import threading

server_data = []
clients = []

#Client thread class
class clientThread(threading.Thread):

	def __init__(self,conn,address):
		
		threading.Thread.__init__(self)
		
		self.connection = conn
		self.address = address
		clients.append(self)
		
	def run(self):
		
		print(f"Created client for {self.address}")
		
		while(1):
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
		HOST = socket.gethostbyname(hostname)
		PORT = 1337

		self.server.bind((HOST,PORT))
		self.server.settimeout(20)
		
	def run(self):
		
		print("Running")
		
		self.server.listen(1)
		
		while(1):
			
			try:
				conn,address = self.server.accept()
				print("Connected to " + address[0])
				message_to_send = "Hello Beautiful!\n".encode("UTF-8")
				conn.send(message_to_send)
				
				#Create a new client, automatically adding it to the clients list
				new_client = clientThread(conn,address)
				new_client.start()
				
				#Clears the connection and the address for the next loop
				conn = NULL
				address = NULL
			
			except:
				print("Connection fail")
				
		conn.close()
		

#Multithreading. One thread is now always listening for new connections and client data
		
thread = connectingThread(1)
thread.start()




	
	
	
	
	
