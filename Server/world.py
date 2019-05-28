from event import *

def testWorld():
	e = Event("render_list", {})

	ground = []
	for i in range(10):
		for j in range(10):
			ground.append({"texture" : "grass_00", "x" : i, "y" : j})
			
	e.args["ground"] = ground
	return e