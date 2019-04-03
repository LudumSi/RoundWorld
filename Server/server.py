
import structure
import socket
import threading
import sys
import time
#from parser import parse

#--Gotta add message length to command header
#--use global locked queue to send things between threads (included in python)
#--see: async io 

class lockArray(object):
	
	def __init__(self,name):
		self.queue = []
		self.array = []
		self.name = name
		
	def acquire(self,thread):
		#Could probably just pass the thread itself rather than the threadID
		
		if thread not in self.queue:
				
			self.array.append(thread)
			
			'''
			if thread != "main":
				print(self.name)
				print(f"Queue: {self.queue}")
				print(f"Array: {self.array}")
			'''
			
	#Assumes the thread doing the releasing is the one which has already aquired it
	def release(self):
				
		self.queue.pop(0)

		if self.name != "data":
			'''
			print(f"{self.name} Released")
			print(f"Queue: {self.queue}")
			'''
		
server_data = []
clients = lockArray("clients")
threads = lockArray("threads")

running = True
		
#Client thread class
#--Python's threads don't run concurrently.

class shittyPrestonThread(threading.Thread):
	
	def queueArray(self,array):
		
		array.acquire(self)
		locked = True
		while locked:

			print(f"Looking for {self} in {array.queue}")
			print(f"{array.name}: Queue: {array.queue}")
			print(f"{array.name}: Array: {array.array}")

			if array.queue[0] == self:
				locked = False
	
	def __init__(self):
		
		super().__init__()
		
		self.queueArray(threads)
		threads.array.append(self)
		threads.release()
		
class clientThread(shittyPrestonThread):
	
	def __init__(self,conn,address):
		
		super().__init__()
		
		self.connection = conn
		self.address = address
	
	#--all client stuff should be in this function   async, await
	#--put broadcast in background thread
	
	#--look into asyc io
	def run(self):
		
		#--don't global
		global running
		print(running)
		print(f"Created client for {self.address}")
		
		while(running):
			#--Arg is bytes recieved, doesn't always work bc of data rerouteing and other stuff.
			try:
				data = self.connection.recv(1024)
				#--got to continue reading until we get entire command
				
				#parser.parse(data) Doesn't return anything, so why is it here?
				#print("Server recieved: %s" % data)
				
				if data:  # != b'':
					#--increases thread refrence count, which makes it take longer to get rid of
					server_data.append((self, data))
					
					#--put command listeners here, and make sure client is still connect
			except:

				pass
		
	def disconnect(self):
		
		print(f"Current players: {len(clients.array)}")

		print(f"Disconnecting from {self.address}!")
		
		print(f"Clients: {clients.array}")
		
		print(f"Removing {self} from clients")
		
		self.queueArray(clients)
		print("Queued Disconnect Sequence")
		clients.array.pop(clients.array.index(self))
		print(f"Deleted Client from Array {clients.array}")
		clients.release()
		print("Removed from clients")
		print(f"Clients: {clients.array}")
		print("Closing connection")
		
		self.connection.shutdown(socket.SHUT_RDWR)
		self.connection.close()
		
		print("Stopping thread")
		
		self.join()
		
		del self

	#Connecting thread class
class connectingThread(shittyPrestonThread):

	def __init__(self):
		
		print("Initializing")
		
		super().__init__()
		
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
		
#--don't need this
class safetyThread(shittyPrestonThread):
		
		def __init__(self):
			
			super().__init__()
		
		def run(self):
			
			global running
			
			while(running):
				
				user_input = input()
			
				if(user_input == "q"):
					running = False
		
#Multithreading. One thread is now always listening for new connections and client data

#--use thread pool to handle all these threads

#--Can use ctrl c to end this, which can be caught with... something
def main():
	safety = safetyThread()
	safety.daemon = True
	safety.start()

	thread = connectingThread()
	thread.daemon = True
	thread.start()

	while(running):
		
		#print(server_data)
		
		for index, data in enumerate(server_data):
			
			sending_client = data[0]
			data_sent = data[1]
			print(f"{sending_client.address}:{data_sent}")
			
			#--should be in the client thread
			if data_sent == b'FFFF{(0:text|bye)}':
				
				if sending_client:
					print("Disconnecting Client")
					sending_client.disconnect()

					#Makes sure the client is actually gone before disconnecting them.
				break
			else:
				
				print("Aquiring main 2")
				clients.acquire("main")
				locked = True
				while locked:
					#print(clients.queue)
					if clients.queue[0] == "main":
						locked = False
				
				for client in clients.array:
				
					if client != sending_client:
					
						message = data_sent
						
						try:
						
							client.connection.send(message) #Need to make sure people are online before sending them messages
							
						except:
							
							pass
						
				clients.release()
			
			server_data.pop(index)

	print("Leaving Loop")
	clients.acquire("main")
	locked = True
	while locked:
		if clients.queue[0] == "main":
			locked = False
			
	for client in clients.array:
		client.join()
		
	clients.release()
		
	print("Shutting down")	
		
			
if __name__ == '__main__':
    main()
		
		
		
		
	


	
	
	
	
	
