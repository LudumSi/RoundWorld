#Generic component that doesnt talk to events
class Component():
	def __init__(self, id, args):
		self.args = args
		self.id = id
		
	def handle(self, event):
		pass
		
	#pushes a component arg into the event
	def eventPush(self, event, argName):
		event.args[argName] = self.args[argName]
		
	#pushes all args into event
	def pushAll(self, event):
		for key, val in self.args:
			event.args[key] = val 
		
	#pulls all relevant args from event into component
	def pullAll(self, event):
		for key in self.args:
			if (event.args[key] != None):
				self.args[key] = event.args[key]
		
	
	

class Render(Component):
	def __init__(self, texture, x, y, theta):
		Component.__init__(self, "renderable", {"texture" : texture, "x" : x, "y" : y, "theta" : theta})
	
	def handle(self, event):
		#contains the 
		if (event.id == "spawn"):
			pushAll(event)
			
		if (event.id == "recvVelUpdate"):
			self.args["x"] = event.args["x"]
			self.args["y"] = event.args["y"]


class Movement(Component):
	def __init__(self, velocity, accel, theta):
		Component.__init__(self, "movement", {"velocty" : velocity, "accel" : accel, "theta" : theta})
	
	def handle(event):
		#send velocity update
		if (event.id == "sendVelUpdate"):
			pushAll(event)
		#recieve velocity update
		elif (event.id == "recvVelUpdate"):
			pullAll(event)


class Collision(Component):
	def __init__(self, vertices):
		Component.__init__(self, "collision", {"vertices" : vertices})
	
	def handle(event):
		if (event.id == "spawn"):
			pushAll(event)
	


class Damaging(Component):
	def __init__(self, type, amount):
		Component.__init__(self, "damaging", {"type" : type, "amount" : amount})
	
	def handle(event):
		if (event.id == "damage"):
			#place type in type list in event
			if (event.args["types"] != None):
				event.args["types"].append(self.args["type"])
			else:
				event.args["types"] = [self.args["type"]]
			
			#place amount in amount list in event
			if (event.args["amounts"] != None):
				event.args["amounts"].append(self.args["amount"])
			else:
				event.args["amounts"] = [self.args["amount"]]
				
				
class Stats(Component):
	#average = 10
	def __init__(self, str, dex, int, cha):
		Component.__init__(self, "stats", {"str" : str, "dex" : dex, "int" : int, "cha" : cha})
	
	def derive(self):
		self.args["move_speed"] = 4 + self.args["dex"] / 10 #speed in pixels
		self.args["stealth"] = self.args["dex"] #added to rolls vs perception
		self.args["perception"] = self.args["int"] #added to rolls vs stealth
		self.args["barter"] = self.args["cha"]  / 1000 #percentage discount
		self.args["physical_damage"] = self.args["str"] / 2 # damage base
		self.args["physical_defense"] = (self.args["str"] + self.args["dex"]) / 4 #defense base
		self.args["magical_damage"] = self.args["dex"]  /2 # damage base
		self.args["magical_defense"] = (self.args["dex"]  + self.args["dex"]) / 4 #defense base
		self.args["support_power"] = self.args["cha"] / 2 # support base
		self.args["attack_speed"] = self.args["dex"] / 1000 #percentage speed increase
		self.args["craft"] = (self.args["dex"] + self.args["dex"] ) / 4 #added to craft rolls
	
	def handle(event):
		pass


class Living(Component):
	def __init__(self, health):
		Component.__init__(self, "living", {"health" : health})
	
	def handle(event):
		if (event.id == "damage"):
			totalDamage = 0
			types = event.args["types"]
			amounts = event.args["amounts"]
			
			#total damage, elemental weakness stuffs will go in here
			for amt in amounts:
				totalDamage += amt
				
			self.args["health"] -= amt
			
class Magical(Component):
	def __init__(self, mana):
		Component.__init__(self, "magical", {"mana" : mana})
	
	def handle(event):
		pass
		
class Staminal(Component):
	def __init__(self, stamina):
		Component.__init__(self, "staminal", {"stamina" : stamina})
	
	def handle(event):
		pass
	
class Specialties(Component):
	def __init__(self):
		Component.__init__("specialties", {"specialties" : []})
	
	def handle(event):
		for spec in self.args["specialties"]:
			spec(event)
	

class Inventory(Component):
	def __init__(self, items):
		Component.__init__("inventory", {"items" : []})
	
	def handle(event):
		if (event.id == "sendInventory"):
			pass
			