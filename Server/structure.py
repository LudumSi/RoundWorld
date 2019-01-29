
import copy

def fire_event(target,type,**params):
	#print('Firing %s with %s at %s' % (type,params,target) )
	new_event = event(type,params)
	target.get_event(new_event)

class event:
	
	def __init__(self,type,params):
		self.type = type
		self.params = params

class game_obj:

	def __init__(self,tags):
		self.comps = []
		self.tags = tags
		self.loc = None
		
	def get_event(self,event):
		self.comps[0].pass_event(event)
		
	def add_comp(self,comp):
		self.comps.append(comp)
		comp.obj = self
		comp.index = self.comps.index(comp)

class game_comp:
	
	default_vars = {}
	
	def __init__(self,config):
		self.obj = None
		self.index = None
		self.config = config
		self.vars = copy.deepcopy(self.default_vars)
		self.vars.update(config)
		
	def pass_event(self,event):
		if self.index == ( len(self.obj.comps) - 1 ):
			del event
		else:
			self.obj.comps[self.index + 1].pass_event(event)

def construct(tags,comps):
	new_obj = game_obj(tags)
	for comp in comps:
		config = comp.config
		new_comp = comp.__class__(config)
		new_obj.add_comp(new_comp)
	return new_obj