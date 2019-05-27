
class Event():
	
	def __init__(self,id,args):
		
		self.id = id #String
		self.args = args #Dictionary of args
		
	def compile(self):
	
		output = self.id + ":" + "{"
		
		for key,val in ennumerate(self.args):
			
			if(isinstance(val,type(1)):
					data += key + ":" + str(val) + ","
			else:
				data += key + ":" + val + ","
				
		data = data[0:len(data) -1] #cut off last comma
		data += "}"
		
		leng = len(data)
		leng += len(str(leng))
		data = str(leng) + "|" + data + "\n"
		
		return data
		
#length|id:{key:val,key:val,key:val}
		
		
