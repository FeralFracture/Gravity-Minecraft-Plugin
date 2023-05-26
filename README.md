# Gravity-Minecraft-Plugin
 A WIP recreating a beta mineplex game, 'Gravity.' The game was set in space in which players would jump from blocks to blocks, gathering fuel when it spawned to refill their fuel meter, and oxygen at the center as the oxyegen level slowly depleted over time. The goal is to take out other players. The players can jump from block to block if theyre grounded against it by dropping their tool. They can also fire knock back arrows with their axe. The knockback arrows affect nearby players with knockback whenever it collides with an entity or surface. The goal of the game is to knock other players into the depths of space. 
 
 Currently, only the physical mechanics are in place.
 - Jumping 
 - Jetpack thrust 
 - Knockback arrow launching
 - various collision detection and player placement
 - (The physics tends to break if the player moves into a corner between blocks, its reccomended that block surfaces are flate in the x or z direction as walls or floors/ceilings.)   
- commands for testing purposes

 Current ingame functionalities
- Right click with an iron sword to apply thrust in a direction
- right click with an iron axe to fire knockback arrow
- if 'Grounded' (stopped moving due to colliding with a block) drop iron sword/axe to 'jump' in facing direction.
- /spawnvehicle to create and ride the arrow
- /resetArrows to delete all arrows
