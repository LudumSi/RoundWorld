import structure

def generate(length, command, components, component_vals, ip):
    
	data = length + "L" + command + "{("

	for i in range(len(components)):
		data += components[i]
		for x in range(len(component_vals[i])):

	return data

def parse(data):
	
	start = 0
	component_num = -1
	val_num = 0
	command_id = ""
	ip = []
	length = 0
	components = []
	component_vals = []

	for i in range(len(data)):
		if data[i] == "L":
			length = int(data[0: i]) #<length>
			start = i + 1
			print("Getting Length")

		elif data[i] == "{":
			command_id = data[i - 4: i] #<command_id
			start = i + 2
			print("Getting Command")
			
		elif data[i] == "(": #
			components.append(data[start: start + 4]) #command_args
			component_vals.append([0] * 4) #needs to be variable for different numbers of values
			component_num += 1
			start = i
			val_num = 0
			print("Getting Component")


			for x in range(start, len(data)): #command
				print(start)
				if data[x] == ")":
					ip.append(data[start + 1: x])
					start = x + 2
					print("End of Component")
					break;
				elif data[x] == ":":
					print("Getting First Comp_val")
					start = x
				elif data[x] == ",":
					print("Getting Comp_Val")
					print(data[start + 1: x])
					component_vals[component_num][val_num] = data[start + 1: x]
					val_num += 1
					start = x
				elif data[x] == "|":
					component_vals[component_num][val_num] = data[start + 1: x]
					start = x
					print("Getting IP")
					start = x
	
	print(length)
	print(command_id)
	print(components)
	print(component_vals)
	print(ip)

parse("10L0101{(2020:val1,val2|123.456.789)(4040:val3,val4,val5,val6|222.222.789)(4040:val3,val4|222.222.789)}")

        
    #<length>Lxxxx{(xxxx:text|<ip>)}
    #<length>L<command_id>{(<component_args>:<component_vals>)}
