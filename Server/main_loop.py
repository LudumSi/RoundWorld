
from threading import Thread
from data_reader import *
from player import *
from area import *

class MainLoopThread(Thread):
	
	def __init__(self, threads, queue, control):
	
		Thread.__init__(self)
		self.queue = queue
		self.control = control
		self.threads = threads
		
	
	
	def send(self, target, event):
		self.threads[target]
	
	def run(self):
		
		#Game loop
		print("reading players...")
		self.players = load_players()
		
		print("Started Main Loop Thread\n")
		while self.control.running:
				self.handleEvents()
				
		
		print("Closing Main Loop Thread\n")
		#save_players()
		
		
	def playerExists(self, name):
		for i in range(len(self.players)):
			if (self.players[i].get_component_by_id("name_data").args["name"] == name):
				return i
		return -1
			
			
	def areaExists(self, name):
		for i in range(len(self.areas)):
			if (self.areas[i].get_component_by_id("name") == name):
				return i
		return -1
	
	def handleEvents(self):
		if (len(self.queue) > 0):
			event = self.queue.pop()
				
			if event.id == "spawn_player":
			
				#create response
				e = Event("re:spawn_player")
				
				#check if the player exsists
				idx = playerExists(event.args["name"])
				if not idx == -1 :
					player = self.players[idx]
					e.args["success"] = 1
					
					#load player data into event
					name_data = player.get_component_by_id("name_data")
					e.args["name"] = name_data.args["name"]
					e.args["name_color"] = name_data.args["name_color"]
					e.args["x"] = player.get_component_by_id("renderable").args["x"]
					e.args["y"] = player.get_component_by_id("renderable").args["y"]
				
				
					if event.args["isClient"] == 1:
						e.args["isClient"] = 1
					else:
						e.args["isClient"] = 0
				else:
					e.args["success"] = 0
				
				#send it
				send(event.args["THREAD_ID"], e)
				
			elif event.id == "new_player":
				players.append(construct_player(event.args))
				
			elif (event.id == "render_request"):
				e = Event("render_list")
				idx = areaExists(event.args["area"])
				if (not idx == -1):
					area = self.areas[idx]
					e.args["success"] = 1
					
					e.args["ground"] = area.get_component_by_id("ground").args
					e.args["surface"] = area.get_component_by_id("surface").args
					e.args["roof"] = area.get_component_by_id("roof").args
					e.args["players"] = area.get_component_by_id("players").args
				else:
					e.args["success"] = 0
					
				send(event.args["THREAD_ID"], e)
			
