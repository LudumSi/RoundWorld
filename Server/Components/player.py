
import structure
import server

class player(structure,game_comp):
	
	comp_id = 0x2
	
	default_vars = {"client":0 #Will be used to store a client_thread, or whatever else we build to inferface between networking and game logic
					}

	def pass_event(self,event):
		
		super().pass_event(event)