
#Temporary workspace, using this to make sure game logic is working

import structure

class rendering(structure.game_comp):
	
	comp_id = 0x1
	
	default_vars = {"x":0,
					"y":0,
					"r":0,
					"texture":""}
					
	def pass_event(self,event):
		
		super().pass_event(event)
		
class printer(structure.game_comp):
	
	id = 0x2
	
	def pass_event(self,event):
		
		self.super()

test_obj = structure.construct([rendering({})])

structure.fire_event(test_obj, "get_var", var_name = "x", comp_id = 0x1)

