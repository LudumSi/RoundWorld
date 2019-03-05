
import structure
import socket
import threading
import sys
import parser

#--Gotta add message length to command header
#--use global locked queue to send things between threads (included in python)
#--see: async io 

class lockArray(object):
	
	def __init__(self):
		self.queue = []
		self.array = []
		
	def acquire(self,thread):
		#Could probably just pass the thread itself rather than the threadID
		
		if thread not in self.queue:
			
			queue.append[threadID]
	
	#Assumes the thread doing the releasing is the one which has already aquired it
	def release(self,threadID):
		
		self.queue.pop[0]
		
server_data = lockArray()
clients = lockArray()
threads = lockArray()

running = True
		
#Client thread class
#--Python's threads don't run concurrently.

class shittyPrestonThread(threading.Thread):
	
	def queueArray(self,array):
		
		array.acquire()
		locked = True
		while locked:
			if array.queue[0] == self:
				locked = False
	
	def __init__(self):
		
		super().__init__(self)
		
		self.queueArray(threading)
		threads.array.append(self)
		threads.release()
		
class clientThread(shittyPrestonThread):

	def __init__(self,conn,address):
		
		super().__init__(self)
		
		self.connection = conn
		self.address = address
		
		self.queueArray(clients)
		clients.array.append(self)
		clients.release()
	
	def run(self):
		
		global running
		
		print(f"Created client for {self.address}")
		
		while(running):
			
			#--Arg is bytes recieved, doesn't always work bc of data rerouteing and other stuff.
			try:
				data = self.connection.recv(1024)
				#parser.parse(data) Doesn't return anything, so why is it here?
			#print("Server recieved: %s" % data)
				self.queueArray(server_data)
				server_data.array.append((self, data))
				server_data.release()
			
			except:
				
				pass
				
	def disconnect(self):
		
		print(f"Disconnecting from {self.address}!")
		
		print(f"Clients: {clients.array}")
		
		print(f"Removing {self} from clients")
		
		self.queueArray(clients)
		clients.array.pop(clients.array.index(self))
		clients.release()
		
		print("Removed from clients")
		
		self.connection.close()
		
		print("Closing connection")

	#Connecting thread class
class connectingThread(shittyPrestonThread):

	def __init__(self):
		
		print("Initializing")
		
		super().__init__(self)
		
		self.server = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
		
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
				
				sele.queueArray(clients)
				
				#Apparently important code to make sure the client doesn't connect multiple times
				for client in clients.array:
					
					if client.connection != conn:
					
						print("Connected to " + address[0])
						message_to_send = "Hello Beautiful!\n".encode("UTF-8")
						new_client.connection.send(message_to_send)
						
						new_client = clientThread(conn,address)
						new_client.daemon = True
						clients.array.append(new_client)
						new_client.start()
						
				clients.release()
				
				#Create a new client, automatically adding it to the clients list
				
				#Clears the connection and the address for the next loop
				conn = 0
				address = 0
			
			except Exception as e:
				print(e)
				
		conn.close()
		print("Closed listener")
		
class safetyThread(shittyPrestonThread):
		
		def __init__(self):
			
			super().__init__(self)
		
		def run(self):
			
			global running
			
			while(running):
				
				user_input = input()
			
				if(user_input == "q"):
					running = False
			
		
#Multithreading. One thread is now always listening for new connections and client data

safety = safetyThread()
safety.daemon = True
safety.start()

thread = connectingThread(1)
thread.daemon = True
thread.start()

parser.parse("1L0101{(2020:efewfew,fewfefw|1111)(4040:efewfew,3fee|2222)}")
while(running):

	server_data.lock.acquire()
	
	for index, data in enumerate(server_data.array):
		
		sending_client = data[0]
		data_sent = data[1]
		print(f"{sending_client.address}:{data_sent}")
		
		if data_sent == b'FFFF{(0:text|bye)}':
			
			if sending_client:
				
				sending_client.disconnect()
				#Makes sure the client is actually gone before disconnecting them. UNTESTED.
		
		else:
			
			clients.lock.acquire()
			
			for client in clients.array:
			
				if client != sending_client:
				
					message = data_sent
				
					client.connection.send(message) #Need to make sure people are online before sending them messages
					
			clients.lock.release()
		
		server_data.array.pop(index)
	
	server_data.lock.release()

print("Shutting down")	
	
sys.exit()
		

		
		
		
		
	


	
	
	
	
	
