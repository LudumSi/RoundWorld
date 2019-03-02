import structure


def generate():
    #Todo

def parse(data):
    start = 0
    command_name = ""
    length = 0
    command_args = ""
    command_vals = []
    ip = ""
    for i in data:
        
		if data[i] = "L":
            length = (int) data[start:i]
            command_name = data[i+1:i+4]
            component_args = data[i+7:i+10]
        
		elif data[i] = ":":
            start = i + 1
        
		elif data[i] = ",":
            command_vals.append(data[start:i-1])
            start = i + 1
        
		elif data[i] = "|":
            start = i + 1
        
		elif data[i] = ")"
            ip = data[start: i - 1]
        
    #<length>Lxxxx{(xxxx:text|<ip>)}
    