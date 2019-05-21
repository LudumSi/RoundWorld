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

class Render(Component){
	def __init__(self, texture, x, y, theta){
		super(self, "renderable", {"texture" : texture, "x" : x, "y" : y, "theta" : theta})
	}
	def handle(event){
		#contains the 
	}
}

class Movement(Component){
	def __init__(self, velocity, accel, theta){
		super(self, "movement", {"velocty" : velocity, "accel" : accel, "theta" : theta})
	}
	def handle(event){

	}
}

class Collision(Component)
	def __init__(self, vertices){
		super(self, "collision", {"Vertices" : vertices})
	}
	def handle(event){

	}
}

class Damaging(Component){
	def __init__(self, type, amount){
		super(self, "damaging", {"type" : type, "amount" : amount})
	}
	def handle(event){
		
	}
}