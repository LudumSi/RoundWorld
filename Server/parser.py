import structure


def generate():
    #Todo

def parse(data):
      start = 0
      command_id = ""
      length = 0
      components = []
      component_vals = [][]
      ip = ""

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

                  for i in range(start, len(data)]: #command
                        if data[i] == ")":
                              break;
                        elif data[i] == "":



            command_name = data[i+1:i+4]
        
    #<length>Lxxxx{(xxxx:text|<ip>)}
    #<length>L<command_id>{(<component_args>:<component_vals>)}

def get_len(data):
      for i in data:
            start = 0
            if data[i] == "L":
                  return data[start: i-1]
            else:
                  return 0

def get_command(data, )
def get_comp(data, start):
      for i in range(start, length(data)):
            return 