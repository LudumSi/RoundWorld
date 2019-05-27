

import socket

import json

from main_loop import MainLoopThread
from control_thread import ControlThread

from threading import Thread
from socketserver import ThreadingMixIn

control_thread = ControlThread()
control_thread.start()

queue = []
testRenders = "74L0002{(4:grass_00,50,50,0,)(4:grass_00,100,50,0,)(4:grass_00,50,100,0,)}"

class ClientThread(Thread):

	def __init__(self,ip,port):
		Thread.__init__(self)
		self.ip = ip
		self.port = port
		self.running = True
		print(f"[+] New server socket thread started for {ip} : {port}")


	def run(self):
		global testRenders
		
		
		
		#client update loop
		while self.running:
			#get data
			data = conn.recv(2048)
			print(f"Server received data: {data}")
			queue.append(json.loads(data))

			#check for disconnect
			if data == b'FFFF{(0:text|bye)}':
				print("Disconnect Sequence")
			
				break
				
			#echo data
			conn.send(data)
			
		#disconnect stuff goes here
		threads.remove(self)
		conn.close()
			
	def abort(self):
		print("Killing thread...")
		self.running = False

TCP_IP = 'localhost'
#TCP_IP = '128.193.254.43'

TCP_PORT = 7777
BUFFER_SIZE = 128

#setup server
tcpServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpServer.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
tcpServer.bind((TCP_IP, TCP_PORT))
tcpServer.settimeout(0.4)

main_loop = MainLoopThread(queue,control_thread)
threads = []#main_loop

main_loop.start()

print("Started connection loop")
while control_thread.running:
	#print(threads)
	tcpServer.listen(4)
	print("Multithreaded Python server : Waiting for connections from TCP clients...")
	#get accept
	
	try:
		(conn, (ip,port)) = tcpServer.accept()
		newthread = ClientThread(ip,port)
		
		newthread.start()
		threads.append(newthread)
		
		print(f"Threads: {len(threads)}")
	
	except socket.timeout:
		
		pass

for t in threads:
	t.abort()
	
