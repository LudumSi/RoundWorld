#Generic component that doesnt talk to events
class Component():
	def __init__(self, id, args):
		self.args = args
		self.id = id
		
	def handle(event):
		pass
	
	#compiles data into json string format for sending
	def compile(self):
		global COMPONENT_ID_LEX
		data = self.id + ":"
		data += "{"
		
		for key, val in self.args.items():
			if (isinstance(val, type(1))):
				data += key + ":" + str(val) + ","
			elif (isinstance(val, Game_object)):
				data += key + ":{" + val.compile() + "},"
			else:
				data += key + ":" + val + ","
			
		data = data[0:len(data) -1] #cut off last comma
		data += "}"
		
		return data
		
