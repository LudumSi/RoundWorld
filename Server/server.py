
import structure
import socket
import threading

#global server_data = []

class client():

	def __init__(self,conn,adress):
		
		self.connection = conn
		self.address = address

class connectingThread(threading.Thread):

	def __init__(self,threadID):
		
		print("Initializing")
		
		threading.Thread.__init__(self)
		self.threadID = threadID
		
		self.server = socket.socket(socket.AF_INET,socket.SOCK_STREAM)

		HOST = "10.248.220.192"
		PORT = 1337

		self.server.bind((HOST,PORT))
		self.server.settimeout(10)
		
	def run(self):
		
		print("Running")
		
		self.server.listen(1)
		while(1):
			conn,address = self.server.accept()
			print("Connected to " + address)
			conn.send(b"It's alive!")
			conn.close()

thread = connectingThread(1)

thread.start()
	
	
	
	
	
