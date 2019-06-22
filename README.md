# PlanetSystem
This system is created for the CosmicSky server.<br/>
It manages regions as planets (it's a kind of a special skyblock-system).

Authors
-

Yasin Dalal (DevSnox) and Lars Artmann (LartyHD)

Dependencies
-

- Kotlin (https://kotlinlang.org/)
- Lombok (https://projectlombok.org/)
- MongoDB (https://mongodb.github.io/mongo-java-driver/)
- Morphia (https://github.com/MorphiaOrg/morphia/)
- SpigotAPI (https://www.spigotmc.org/)
- Darkness (https://github.com/DevSnox/DarkBedrock)
- Fawe(API) (https://github.com/boy0001/FastAsyncWorldedit)
- Worldedit (https://github.com/EngineHub/WorldEdit)
- Log4j (https://github.com/apache/log4j)
- VaultAPI (https://github.com/MilkBowl/VaultAPI)
- DynamicMinecraftNetwork(API) (https://github.com/DevSnox/DynamicMinecraftNetwork)

Changelog:
-

v.1.1.0:
- :heavy_plus_sign: Added planet history (Possibility to restore planets)
- :heavy_plus_sign: Added planet ban and unban feature
- :heavy_plus_sign: Added planet planet enter and leave event
- :heavy_plus_sign: Added check on planet load if the planet isn't loaded already
- :heavy_plus_sign: Added planet lock command
- :heavy_plus_sign: Added planet kick command
- :heavy_plus_sign: Added configurable database credentials
- :heavy_plus_sign: Added planet visit command
- :heavy_plus_sign: Added command member count permissions (Planet Members (Permission) #51) 

- :arrows_counterclockwise: Changed default atmosphere type to cuboid
- :arrows_counterclockwise: Changed /planet expand to /planet atmosphere

- :arrow_up: Improved the saving of the schematic -> the atmosphere doesn't get saved anymore in the schematic
- :arrow_up: Updated to Kotlin 1.3.40
- :arrow_up: Generally improved code-style

- :heavy_check_mark: Fixed a Atmosphere Database bug 
- :heavy_check_mark: Fixed planet delete command
- :heavy_check_mark: Fixed lombok
- :heavy_check_mark: Fixed outer region clearing
- :heavy_check_mark: Fixed planet sethome command
- :heavy_check_mark: Fixed now the system unloads visited planets after leaving
- :heavy_check_mark: Fixed some issues with Morphia
- :heavy_check_mark: Fixed pistions can't anymore push blocks out of the inner region
- :heavy_check_mark: Fixed projectile launch by not members
- :heavy_check_mark: Fixed itemframes, armorstands and paintings can't be destroyen anymore from not members
- :heavy_check_mark: Fixed lava and water flowing (liquids doesn't flow out of the inner region)

- :heavy_minus_sign: Removed a lot of old debug messages
  
v.1.0.1
- fixed a false new line (\n) in the code 
- Improve code structure (with kotlin high level variables) 
- Planet Restart #54 #145 review by DevSnox (configurable (messages and inventory)) 
- Planet Restart #54
- Merge pull request #129 from DevSnox/gradle-version-administration
- Make the grid max size configurable #118 
- Set Outer Region to grid maxSize #137 
- Make the planet-world name configurable #117
- Save the Atmosphere of the Planet in Database #83
- gradle version administration (Updated versions (used gradle.properties) #128)
- #119 review changes (added configuration to Flags)
- #104 review changes (use MessageKeys)
- Added DISPENSER, DROPPER, HOPPER, STONE_BUTTON, STONE_PLATE, TRAP_DOOR, LEVER, all DOORs and all FENCE_GATEs to the outsider - blocked list
- Fixed the outer region clearing
- Protection bug #105
- fixed a test
- Fixed ExpandCommand args LartyHD
- Felder zertrampeln blockieren #100 (fixed)
- "/planet expand soll nur ausführbar auf der eigenen Insel sein #62" (with test)
- Impl "Delete the planets world on disable #114" 
- #107 Fix pvp property (and added a test for this case)
- Added version replacer #116
- Added the review suggestions from DevSnox #104 
