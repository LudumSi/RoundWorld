import structure


def generate():
    #Todo

def parse(data):
      start = 0
      component_num = 0
      val_num = 0
      command_id = ""
      ip = []
      length = 0
      components = []
      component_vals = [][]

      for i in data:
            start = 0
            if data[i] == "L":
                  length = (int) data[start: i - 1] #<length>
                  start = i + 1
            elif data[i] == "{":
                  command_id = data[start: start + 3] #<command_id
                  start = i + 2
            elif data[i] == "(": #
                  components.append(data[start: start + 3]) #command_args
                  component_num ++

                  for x in range(start, len(data)]: #command
                        if data[x] == ")":
                              ip[component_num] = data[start + 1: x - 1]
                              break;
                        elif data[x] == ":":
                              start = x
                        elif data[x] == ",":
                              val_num ++
                              component_vals[component_num][val_num] = data[start + 1: x - 1]
                              start = x
                        elif data[x] == "|":
                              start = x
            print(length)
            print(command_id)
            print(components)
            print(component_vals)
            print(ip)




            #command_name = data[i+1:i+4]
        
    #<length>Lxxxx{(xxxx:text|<ip>)}
    #<length>L<command_id>{(<component_args>:<component_vals>)}