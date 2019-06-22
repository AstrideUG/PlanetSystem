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
- Added planet history (Possibility to restore planets)
- Added planet ban and unban feature
- Added planet planet enter and leave event
- Added check on planet load if the planet isn't loaded already
- Added planet lock command
- Added planet kick command
- Added configurable database credentials
- Added planet visit command
- Added command member count permissions (Planet Members (Permission) #51) 

- Improved the saving of the schematic -> the atmosphere doesn't get saved anymore in the schematic

- Changed default atmosphere type to cuboid
- Changed /planet expand to /planet atmosphere

- Fixed a Atmosphere Database bug 
- Fixed planet delete command
- Fixed lombok
- Fixed outer region clearing
- Fixed planet sethome command
- Fixed now the system unloads visited planets after leaving
- Fixed some issues with Morphia
- Fixed pistions can't anymore push blocks out of the inner region
- Fixed projectile launch by not members
- Fixed itemframes, armorstands and paintings can't be destroyen anymore from not members
- Fixed lava and water flowing (liquids doesn't flow out of the inner region)

- Removed a lot of old debug messages

- Updated to Kotlin 1.3.40

- Generally improved code-style
  
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
