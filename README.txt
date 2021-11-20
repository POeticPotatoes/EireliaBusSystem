 ~ Eirelia Bus System ~

A plugin that provides a bus service for the Eirelia Minecraft server.
Bus stops are defined by signs and will be deleted if the sign is broken.

------------------------------------------------

~ Sign format ~

 <additional info>
 <Bus stop name>
 <Bus stop name>
 <Bus line>

Additional info - Any additional text
Bus Stop Name - The name of the bus stop. 
	        Two lines are available for naming,
	        and they will be joined with a space between.

           Eg. hello
               world  gives "hello world"

Bus line - The name of the line that the bus belongs to.
           This determines what other bus stops will be available
	   from the bus stop GUI.

-------------------------------------------------

~ Config file ~

economy_multiplier - float value that determines how expensive
		    bus stops will be. The price to travel to
		    another stop is given by

			distance_in_blocks * economy_multiplier

		    default value: 0.01

travel_settings - settings for what happens during the bus travel.
	
	| delay - the delay(in seconds) between display text. 
	|  	  Only accepts integers, default value of 2.
	| 
	| blindness - determines whether to apply blindness after 
	|	     the teleport. Default value of true.
	|
	| text - a list of text to be shown to the player during travel.
	|	this means that travel time can be calculated by
	|		
	|		delay * (no_of_lines - 1)

--------------------------------------------------

~ Commands ~

/cancelbus
   Aka. /cancel
   Permission: eirelia.bus.use
	Cancels an existing ride if the player is currently travelling.

/createbus
   Aka. /newbus
   Permission: eirelia.bus.edit
	Creates a new bus stop if the player is looking at a sign.
	Player will be asked to specify where they want players to spawn,
	and also where they want the player to face upon spawning.
	Sign must be a valid bus stop (name and line must be specified.).

/setbusmultiplier
   Aka. /multiplier
   Permission: eirelia.bus.edit
	Changes the multiplier for bus prices to a specified value.
	Changes will be reflected in config.yml as economy_multiplier.

/reloadbusconfig
   Permission: eirelia.bus.admin
	Updates the plugin based on any changes to the config file
	while the server is running.
	Does not require a restart for changes to take effect.


-------------------------------------------------------
