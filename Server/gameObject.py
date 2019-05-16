
'''

ids: 

0: text


'''

COMPONENT_ID_LEX = {
	"text" : 0,
	"position" : 1,
	"velocity" : 2,
	"entity" : 3,
	"inventory" : 10

}


# "74L0002{(4:grass_00,50,50,0,)(4:grass_00,100,50,0,)(4:grass_00,50,100,0,)}"
class Component():
	def __init__(self, id, args):
		self.args = args
		self.id = id
		
	#compiles data into string format for sending
	def compile(self):
		global COMPONENT_ID_LEX
		data = "("
		data += str(COMPONENT_ID_LEX[self.id])
		data += ":"
		for val in self.args.values():
			data += val + ","
		data += ")"
		print(data)
		return data


class Game_object():
	def __init__(self):
		self.components = []
		
	def addComponent(self, c):
		self.components.append(c)
		
	
	#compiles Game_object to send to client, with context as the command id so the client knows what to do with it
	def compile(self, context):
		data = "L" + context + "{"
		for comp in self.components:
			data += comp.compile()
			
		data = data + "}"
		leng = len(data)
		leng += len(str(leng))
		data = str(leng) + data
		return data
		
		
		