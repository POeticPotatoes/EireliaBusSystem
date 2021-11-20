#Eirelia Bus System
---
A plugin that provides a bus service for the Eirelia Minecraft server.
Bus stops are defined by signs and will be deleted if the sign is broken.

##Table of Contents
--- 
* [Introduction] (#Introduction)
* [Technologies] (#Technologies)
* [Permissions] (#Permissions)
* [Sign Format] (#Sign Format)
* [Config File] (#Config File)
* [Commands] (#Commands)

##Introduction
---
A java plugin written for the Eirelia Server, designed to provide a bus service for  
players that charges them based on distance. Bus stops are defined by the placement  
of signs which provide a stop name and a bus line; only stops within the same line  
are visible to each other.

Written by POeticPotatoes, who is learning and practicing Object-Oriented Programming.

##Technologies
---
Java Version: JavaSE-1.8
Written in the Eclipse IDE
Managed with Apache Maven

Config file: Written in YAML with standard notation

Dependencies:
* Spigot 1.16.4 API (hub.spigotmc.org)
⋅⋅* Server must be running on Spigot or a version based on Spigot (eg.paper)
* VaultAPI 1.7 (github.com.MilkBowl/VaultAPI)
⋅⋅* Solely for economy API (to handle bus prices and charging)
⋅⋅* Server MUST include Vault and a vault-compatible economy plugin

##Permissions
---
Players will be limited to certain uses of the plugin based on the permissions they  
are granted. These permissions can be managed by any vault-compatible permissions  
manager (Eg. Group Manager).

In order of administrative power:
* **eirelia.bus.use**
  Grants the player permission to use bus stops to teleport, along with the */cancelbus*  
  command to cancel any existing bus rides.
* **eirelia.bus.edit**
  Grants the player permission to create new bus stops and delete existing ones.
  Also grants access to the */setbusmultiplier* command.
  Automatically grants the permission of **use**.
* **eirelia.bus.admin**
  Grants the player permission to use the */reloadbusconfig* command.
  Automatically grants the permissions of **use** and **edit**.

##Sign Format
---
The following is the format that should be used when creating a new bus stop with a  
sign:

* additional info
* Bus stop name
* Bus stop name
* Bus line

* **Additional info** - Any additional text
* **Bus Stop Name** - The name of the bus stop. 
  	        Two lines are available for naming,
  	        and they will be joined with a space between.
           Eg. *hello*
               *world*  gives "hello world"

* **Bus line** - The name of the line that the bus belongs to.
               This determines what other bus stops will be available
  	   from the bus stop GUI.

In order to register a sign as a bus stop, a player should look at the sign and use  
the /createbus command. They will then be asked to left click a block for incoming  
players to be teleported to and a direction to face.

##Config File
---
* **economy_multiplier** - float value that determines how expensive
  		    bus stops will be. The price to travel to
  		    another stop is given by
            distance_in_blocks * economy_multiplier

            default value: 0.01

* **travel_settings** - settings for what happens during the bus travel.
    | * delay - the delay(in seconds) between display text. 
    |  	  Only accepts integers, default value of 2.
    | 
    | * blindness - determines whether to apply blindness after 
    |	     the teleport. Default value of true.
    |
    | * text - a list of text to be shown to the player during travel.
    |	this means that travel time can be calculated by
    |		
    |		delay * (no_of_lines - 1)


##Commands
---
**/cancelbus**
   Aka. /cancel
   Permission: *eirelia.bus.use*
	Cancels an existing ride if the player is currently travelling.

**/createbus**
   Aka. /newbus
   Permission: *eirelia.bus.edit*
	Creates a new bus stop if the player is looking at a sign.
	Player will be asked to specify where they want players to spawn,
	and also where they want the player to face upon spawning.
	Sign must be a valid bus stop (name and line must be specified.).

**/setbusmultiplier**
   Aka. /multiplier
   Permission: *eirelia.bus.edit*
	Changes the multiplier for bus prices to a specified value.
	Changes will be reflected in config.yml as economy_multiplier.

**/reloadbusconfig**
   Permission: *eirelia.bus.admin*
	Updates the plugin based on any changes to the config file
	while the server is running.
	Does not require a restart for changes to take effect. 
	(Despite the command warning)

