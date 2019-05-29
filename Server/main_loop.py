
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
				event = self.queue.pop()
				if event.id == "spawn_player":
				
					e = Event("re:spawn_player")
					if event.args["isClient"] == 1:
						e.args["isClient"] = 1
					else:
						e.args["isClient"] = 0
						
					
				pass
		
		print("Closing Main Loop Thread\n")

		
#length|id:{key:val,key:val,key:val}		


			
			
			
	
		
