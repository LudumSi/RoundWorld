

import socket
from threading import Thread
from socketserver import ThreadingMixIn


class ClientThread(Thread):

	def __init__(self,ip,port):
		Thread.__init__(self)
		self.ip = ip
		self.port = port
		print(f"[+] New server socket thread started for {ip} : {port}")


	def run(self):
		while True:
			data = conn.recv(2048)
			print(f"Server received data: {data}")

			if data == b'FFFF{(0:text|bye)}':
				print("Disconnect Sequence")
				

				self.ip.shu
				self.thread..shutdown(socket.SHUT_RDWR)
				self.connection.close()

				#clients.array.pop(clients.array.index(self))
				self.join()
		
				del self

			MESSAGE = input("Multithreaded Python server : Enter Response from Server/Enter exit:")
			if MESSAGE == 'exit':
				break
			conn.send(MESSAGE.encode())


TCP_IP = 'localhost'
TCP_PORT = 7777
BUFFER_SIZE = 128

tcpServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpServer.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
tcpServer.bind((TCP_IP, TCP_PORT))
threads = []

while True:
	tcpServer.listen(4)
	print("Multithreaded Python server : Waiting for connections from TCP clients...")
	(conn, (ip,port)) = tcpServer.accept()
	newthread = ClientThread(ip,port)
	newthread.start()
	threads.append(newthread)
	print(threads)

for t in threads:
	t.join()
