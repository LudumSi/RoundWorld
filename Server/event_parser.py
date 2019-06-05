import json
from event import *


#Outputs the string sans list, throws and exception if the string isn't the same length as the length
def parse(input_string):
	
	#length|id:{key:val,key:val,key:val}
	
	pipe_index = 0
	colon_index = 0
	
	for index, char in enumerate(input_string):
		
		if char == '|':
			pipe_index = index + 1
			
		if char == ':':
			colon_index = index
			break
			
			
	
	sans_length = input_string[pipe_index:]
	#add quotes
	sans_length = "{\"" + sans_length[1:colon_index-pipe_index] + "\"" + sans_length[colon_index-pipe_index:len(sans_length)-3]
			
	
	args = json.loads(sans_length)
	id = list(args.keys())[0]
	e = Event(id, args[id])
	return e
	
