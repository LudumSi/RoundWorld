import json
from player import *
from area import *

items = {}
enemies = {}



def load_players():
	players = []
	file = open("data/players.json")
	p = json.loads(file.read())
	#add players to list
	for player in p.values():
		players.append(construct_player(player))
	file.close()
	return players

def save_players():
	global players
	file = open("data/players.json")
	file.write(json.dumps(players))
	file.close()
	
def load_areas():
	areas = []
	file = open("data/areas.json")
	p = json.loads(file.read())
	#add players to list
	for area in p:
		areas.append(construct_area(area))
	file.close()
	return areas

def save_players():
	global players
	file = open("data/players.json")
	file.write(json.dumps(players))
	file.close()
	
def load_items():
	global items
	file = open("data/items.json")
	players = json.loads(file.read())
	file.close()
	
def save_items():
	global items
	file = open("data/items.json")
	file.write(json.dumps(players))
	file.close()