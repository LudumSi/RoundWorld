

import socket

import json

from main_loop import MainLoopThread
from event_parser import parse
from control_thread import ControlThread

from threading import Thread
from socketserver import ThreadingMixIn
from world import *

control_thread = ControlThread()
control_thread.start()

queue = []


class ClientThread(Thread):

	def __init__(self,id,ip,port):
		Thread.__init__(self)
		self.ip = ip
		self.port = port
		self.running = True
		self.id = id
		print(f"[+] New server socket thread started for {ip} : {port}")


	def run(self):
		global queue
		global parser
		
		#client update loop
		while self.running:
			#get data
			data = conn.recv(2048)
			print(f"Server received data: {data}")
			
			#parse event
			event = parse(str(data))
			#add the thread's id into the event
			event.args["THREAD_ID"] = self.id
			#add to queue
			queue.append(event)
	
			
			

			#check for disconnect
			if data == b'FFFF{(0:text|bye)}':
				print("Disconnect Sequence")
			
				break
			
			
		#disconnect stuff goes here
		threads.remove(self)
		conn.close()
		
	def send(self, event):
		data = event.compile()
		print("sending: " + data)
		print("to " + self.ip)
		conn.send(data.encode())
			
	def abort(self):
		print("Killing thread...")
		self.running = False

TCP_IP = 'localhost'
#TCP_IP = '128.193.254.43'

TCP_PORT = 7777
BUFFER_SIZE = 128

GLOBAL_THREAD_ID = 0

#setup server
tcpServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpServer.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
tcpServer.bind((TCP_IP, TCP_PORT))
tcpServer.settimeout(0.4)

threads = []#main_loop
main_loop = MainLoopThread(threads, queue,control_thread)

main_loop.start()

print("Started connection loop")
while control_thread.running:
	#print(threads)
	tcpServer.listen(4)
	#print("Multithreaded Python server : Waiting for connections from TCP clients...")
	#get accept
	
	try:
		(conn, (ip,port)) = tcpServer.accept()
		newthread = ClientThread(GLOBAL_THREAD_ID, ip,port)
		GLOBAL_THREAD_ID += 1
		
		newthread.start()
		threads.append(newthread)
		
		print(f"Threads: {len(threads)}")
	
	except socket.timeout:
		
		pass

for t in threads:
	t.abort()
	
