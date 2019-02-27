
import structure

class rendering(structure.game_comp):
	
	id = 0x1
	
	default_vars = {"x":0,
					"y":0,
					"r":0,
					"texture":""}
					
	def pass_event(self,event):
		
		self.super()
		
class printer(structure.game_comp):
	
	id = 0x2
	
	def pass_event(self,event):
		
		self.super()

test_obj = structure.construct([rendering({})])
data = structure.fire_event(test_obj,"get_vars",)




	
	
	
