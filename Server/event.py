import json
class Event():
	
	def __init__(self,id,args={}):
		
		self.id = id #String
		self.args = args #Dictionary of args
		

		
		
	def compile(self):

		stri = ""
		stri += "{" + self.id + ":"
		stri += json.dumps(self.args) + "}"
		leng = 0
		leng = len(stri)
		leng += len(str(leng))
		leng+=1
		stri = str(leng) + "|" + stri + "\n"
		
		return stri
		
#length|id:{key:val,key:val,key:val}
		
		
