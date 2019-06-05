from gameObject import *


def construct_area(areaData):
	print("Constructing: " + areaData["name"])
	area = Game_object("area:" + areaData["name"])
	area.add_component(Component("name", areaData["name"]))
	area.add_component(Component("ground", areaData["ground"]))
	#area.add_component(Component("surface", areaData["surface"]))
	#area.add_component(Component("roof", areaData["roof"]))
	area.add_component(Component("players", areaData["players"]))
	return area