
from threading import Thread

class ControlThread(Thread):
	
	def __init__(self):
	
		Thread.__init__(self)
		self.running = True
	
	def run(self):
		
		#Game loop
		print("Started Control Loop Thread\n")
		
		while self.running:
			
			user_input = input()
			
			if(user_input == "quit" or user_input == "q"):
				
				self.running = False
			
#length|id:{key:val,key:val,key:val}		


			
			
			
	
		
