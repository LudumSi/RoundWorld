import json
class Event():
	
	def __init__(self,id,args):
		
		self.id = id #String
		self.args = args #Dictionary of args
		

		
		
	def compile(self):

		j = {self.id : self.args}
		data = json.dumps(j)
		
		return data
		
#length|id:{key:val,key:val,key:val}
		
		
