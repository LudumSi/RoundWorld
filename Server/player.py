from gameObject import *
from component import *




def construct_player():

	player = Game_object("player")


	player.add_component(Render("player", 0, 0, 0))
	player.add_component(Movement(0, 0, 0))
	#might be wrong verts
	player.add_component(Collision([0,0, 0,32, 32,0, 32,32]))
	player.add_component(Stats(10, 10, 10, 10))
	((Stats) player.get_component_by_id("stats")).derive()
	hp = player.get_component_by_id("stats").args["str"] * player.get_component_by_id("stats").args["cha"]
	mana = player.get_component_by_id("stats").args["int"] * 5
	stamina = player.get_component_by_id("stats").args["str"] * 5
	player.add_component(Living(hp))
	player.add_component(Magical(mana))
	player.add_component(Staminal(stamina))
	player.add_component(Specialties())
	
	return player

player = construct_player()
print(player.compile())