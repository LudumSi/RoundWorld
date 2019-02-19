
import ..\structure

class packager(game_comp):
	
	id = 0x2
	
	default_vars = {}
					
	def pass_event(self,event):
		
		self.super()
		
		if event.type == "send_data":
		
			data = string(str(self.obj.comps))
		
	

	
	
	
	
	

