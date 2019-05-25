from component import *


# "74L0002{(4:grass_00,50,50,0,)(4:grass_00,100,50,0,)(4:grass_00,50,100,0,)}"



class Game_object():
	def __init__(self, id):
		self.components = []
		self.id = id
		
	def add_component(self, c):
		self.components.append(c)
		
	def get_component_by_id(self, id):
		for comp in self.components:
			if (comp.id == id):
				return comp
	
	


		