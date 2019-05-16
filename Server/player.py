from gameObject import *


player = Game_object()

player.addComponent(Component("text", {"text": " "}))
player.addComponent(Component("position", {"x":"0", "y":"0"}))
player.addComponent(Component("velocity", {"x":"0", "y":"0"}))
player.addComponent(Component("inventory", {"item": " "}))
player.addComponent(Component("entity", {"health": "10"}))

print("hi")
print(player.compile("0001"))