from gameObject import *

#creates new player with name n
def construct_stat_list(str, dex, int, cha):
	stat_list = Game_object("stat_list")
	stat_list.add_component(Component("core_stats", {"str" : str, "dex" : dex, "int" : int, "cha" : cha}))
	
	stat_list.add_component(Component("pool_stats", {"health" : str * cha, "mana" : int, "stamina" : str}))
	
	stat_list.add_component(Component("move_stats", {"speed" : dex, "stealth" : dex, "perception" : int, "barter" : cha}))
	
	stat_list.add_component(Component("combat_stats", {"phys_dam" : str, "mag_dam" : int, "phys_def" : str, "mag_def" : int, "sup_pow" : cha, "atk_spd" : dex}))
	
	stat_list.add_component(Component("craft_stats", {"craft" : int * dex}))
	print("built")
	print(stat_list.compile())
	return stat_list

def construct_entity(name, stat_list):

	entity = Game_object("stat_list")

	
	entity.add_component(Component("text", {"text": name}))
	entity.add_component(Component("position", {"x":"0", "y":"0"}))
	entity.add_component(Component("velocity", {"x":"0", "y":"0"}))
	entity.add_component(Component("stat_list", {"stats" : stat_list}))
	
	return entity


def construct_player(entity):

	player = Game_object("player")


	player.add_component(Component("entity", {"entity": entity}))
	return player

player = construct_player(construct_entity("test", construct_stat_list(5, 5, 5, 5)))
print(player.compile())