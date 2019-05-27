from event import *

def testWorld():
	Event e = Event()
	e.id = "render_list"
	ground = []
	for i in range(10):
		for j in range(10):
			ground.append({"texture" : "grass", "x" : i, "y" : j})
			
	e.args["ground"] = ground
	return e