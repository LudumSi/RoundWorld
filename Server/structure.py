
import copy

#Function to fire events, usable by everything. Type is the event command, params becomes a dictionary of data.

def fire_event(target,type,**params):
	new_event = event(type,params)
	target.get_event(new_event)

#Event class, stores command and data, and so can be passed around
class event:
	
	#type:string params:dictionary
	def __init__(self,type,params):
		self.type = type
		self.params = params

#Game object class, the building-block of the game
class game_obj:
	
	#Creates an empty list for comps, gets a list of tags from constructor for quick object identification
	def __init__(self):
		self.comps = []
		
	#Takes in an event, and then passes it through the list of events to me modified or manipulated
	def get_event(self,event):
		
		'''if event.type == 'get_obj':
			event.sender.vars['linked_obj'] = self
			
		else:
		'''
		for comp in self.comps:
			comp.pass_event(event)
			if event.type == "null": #Deletes null events
				break

		del event
		
	#Takes in the component to be added, adds it to the object's component list, and tells the component that its owner object is the object
	def add_comp(self,comp):
		
		#Sorts components as they're added
		if comp.priority <= len(self.comps):
			self.comps.insert(comp.priority,comp)
		else:
			self.comps.append(comp)
		
		comp.obj = self

class game_comp:
	
	#The default variables for the component, basically creating the variables which will be used
	default_vars = {}
	comp_id = 0x0
	priority = 0
	
	def __init__(self,config):
		
		#Creates a blank var for the object the component is part of
		self.obj = None
		
		#Takes configuration settings from the contruction function
		self.config = config
		
		#Copies the default variables so they exist to be configured
		self.vars = copy.deepcopy(self.default_vars)
		
		#Changes the default vars to the configured ones
		self.vars.update(config)
	
	#Takes in event, does things. Probably a massive switch statement
	def pass_event(self,event):
		
		if event.type == "change_var":
			
			if event.params["comp_id"] == self.comp_id:
				
				self.vars[event.params["var_name"]] = event.params["var_value"]
				event.type = "null" #Sets the event to be deleted
				
		elif event.type == "get_var":
			
			if event.params["comp_id"] == self.comp_id:
				
				event.params["var_name"] = self.vars[event.params["var_name"]]
				#print(event.params) #Debugging only
				event.type = "null"
			
		#elif event.type == "server_get_vars":
			

# We need to figure out a better way to contruct things...
# Perhaps read from a JSON file to create objects?
def construct(comps):
	
	new_obj = game_obj()
	
	for comp in comps:
		
		config = comp.config
		new_comp = comp.__class__(config)
		new_obj.add_comp(new_comp)
	
	return new_obj
	
	
	
