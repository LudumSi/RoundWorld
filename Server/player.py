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
	player.add_component(Living(100))
	player.add_component(Magical(100))
	player.add_component(Staminal(100))
	player.add_component(Specialties())
	
	return player

player = construct_player()
print(player.compile())