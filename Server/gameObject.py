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
	
	#compiles Game_object to json context as the command id so the client knows what to do with it
	def compile(self):
		data = self.id + ":["
		for comp in self.components:
			data += comp.compile() + ","
		
		data = data[0:len(data) -1] #cut off last comma
		data = data + "]"
		return data
		

#prepares compiled stuff to send to client
def ship(data, context):
	data = "L{" + data + "}"
	#calculate length
	leng = len(data)
	leng += len(str(leng))
	data = str(leng) + data
	return data
		
		