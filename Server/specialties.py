from event import *

#+20% damage with swords
def SwordWielding(event):
	if (event.type == "damage"):
		if (event.args["weaponType"] == "sword"):
			event.args["amounts"][event.args["types"].indexof("normal")] *= 1.2f
			
#crits deal +50% damage
def DaggerWielding(event):
	if (event.type == "damage"):
		if (event.args["weaponType"] == "dagger" and event.args["isCrit"] == True):
			event.args["amounts"][event.args["types"].indexof("normal")] *= 1.5f