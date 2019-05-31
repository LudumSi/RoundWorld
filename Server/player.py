from gameObject import *
from component import *




def construct_player(playerData):

	player = Game_object("player")

	player.add_component(Component("name_data", playerData["name_data"]))
	player.add_component(Render(playerData["texture"], playerData["x"], playerData["y"], 0))
	player.add_component(Movement(0, 0, 0))
	#might be wrong verts
	player.add_component(Collision([0,0, 0,32, 32,0, 32,32]))
	'''player.add_component(Stats(playerData["str"], playerData["dex"], playerData["int"], playerData["cha"]))
	player.get_component_by_id("stats").derive()
	hp = player.get_component_by_id("stats").args["str"] * player.get_component_by_id("stats").args["cha"]
	mana = player.get_component_by_id("stats").args["int"] * 5
	stamina = player.get_component_by_id("stats").args["str"] * 5
	player.add_component(Living(hp))
	player.add_component(Magical(mana))
	player.add_component(Staminal(stamina))'''
	player.add_component(Specialties())
	
	return player

