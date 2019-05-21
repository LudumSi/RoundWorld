def construct_item(name):

	item = Game_object("item")


	item.add_component(Render(name, 0, 0, 0))
	
	#might be wrong verts
	item.add_component(Collision([0,0, 0,32, 32,0, 32,32]))
	
	
	return player
