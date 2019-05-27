
from threading import Thread

class MainLoopThread(Thread):
	
	def __init__(self, queue, control):
	
		Thread.__init__(self)
		self.queue = queue
		self.control = control
	
	def run(self):
		
		#Game loop
		print("Started Main Loop Thread\n")
		while self.control.running:
				pass
		
		print("Closing Main Loop Thread\n")

		
#length|id:{key:val,key:val,key:val}		


			
			
			
	
		
