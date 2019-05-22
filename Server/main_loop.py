
from threading import Thread

class MainLoopThread(Thread):
	
	def __init__(self, queue):
	
		Thread.__init__(self)
		self.queue = queue
		print("Started the main game loop")
	
	#def run(self):
		
		#Game loop
		while self.running:
		#Needs to take something off the queue, parse it, and then do the thing
			print(parse(queue.pop(0)))
		
#length|id:{key:val,key:val,key:val}		
def parser(input_string):

	input = list(input_string)
	
	event_id = ""
	event_args = {}
	
	passed_length = False
	passed_id = False
	
	tmp_string = ""
	tmp_string2 = ""

	#Get rid of everything to the '|'
	while(char[0] != '|'): 
		del input[0]
		
	del input[0] #Get rid of the '|'
	
	while(char[0] != ':'):
		tmp_string.append(input.pop(0))	
		
	event_id = tmp_string
	tmp_string = []
		
	del input[0:1]
	
	while(char[0] != '}'):
		
		while(char[0] != ':'):
			tmp_string.append(input.pop(0))
		
		del input[0]
		
		while(char[0] != ',')
			tmp_string2.append(input.pop(0))
			
		del input[0]
		
		event_args[tmp_string] = tmp_string2
		
		tmp_string = []
		tmp_string2 = []
		
	return Event(event_id,event_args)
	
		
	
		
		
		
	
			
		
			
			
			
	
		