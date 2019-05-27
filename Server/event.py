import json
class Event():
	
	def __init__(self,id,args):
		
		self.id = id #String
		self.args = args #Dictionary of args
		
		
	def __init__(self, j):
		self.id = j.keys[0]
		self.args = j[self.id]
		
		
		
	def compile(self):

		j = {self.id : args}
		data = json.dumps(j)
		
		return data
		
#length|id:{key:val,key:val,key:val}
		
		
