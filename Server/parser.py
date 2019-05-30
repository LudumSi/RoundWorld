

#Outputs the string sans list, throws and exception if the string isn't the same length as the length
def parse(input_string):
	
	#length|id:{key:val,key:val,key:val}
	
	pipe_index = 0
	
	for index, char in enumerate(input_string):
		
		if char == '|':
			pipe_index = index + 1
			break
			
	sans_length = input_string[pipe_index:]
			
	return ''.join(sans_length)
	
testvar = input()
parsedvar = parse(testvar)
print(parsedvar)