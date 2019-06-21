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

v.1.1:
- Added player planet history #174 
- Added Planet Ban and UnBan #142 
- Unload visited planet after leaving #180
- Add planet enter and leave event
- Block launch Projectile by not member #185
- Added ItemFrames and Painting destroy block for not member and Fix ArmorStand can't destroying by member / owner #188
- Added DatabasePlanet.allMembers
- Changed the EventPriority of PlayerBucketFillEvent back to NORMAL
- Fixed a bug with blocked interaction items and blocks #184
- Improved code style in blocked interaction types
- Added MONSTER_EGG, ITEM_FRAME, ARMOR_STAND and PAINTING to blocked interaction materials #184 LartyHD 2019-06-20 18:33
- Added DAYLIGHT_DETECTOR and DAYLIGHT_DETECTOR_INVERTED to protected block types LartyHD 2019-06-20 18:29
- Added GOLD_PLATE, IRON_PLATE, REDSTONE_COMPARATOR_OFF, REDSTONE_COMPARATOR_ON, Material.DIODE_BLOCK_OFF and
- Material.DIODE_BLOCK_ON to protected block types
- Fix lava and water flowing inside inner region #191
- Added "is planet already loaded" check on planet.load() #190
- Added Blocks piston extension in outer region #123
- Fixed PlayerBucketEmptyEvent & PlayerBucketFillEvent block
- Added lava and water flows from outer region block
- Switched implication form planed owner base to player entry based
- Fixed a morphia bug in BasicDatabasePlayer
- Added water and lava flow out of the inner region block #97
- Block ArmorStand manipulation #20
- Rename canBuild to canEdit LartyHD
- Place Atmosphere after saving LartyHD
- Spited Location.planet to Location.innerPlanet and Location.outerPlanet
- Added Planet Lock command #50
- Added /Planet kick <Player> #143
- Changed isInside request to outer region LartyHD
- Fixed a StackOverflowException in VisitCommand teleport(LoadedPlanet)
- Improved code style
- Change /planet expand ... to /planet atmosphere ... #139 
- Reverse visit functions order 
- Fixed planet delete command 
- Updated to Kotlin 1.3.40 
- Change all to proxy implementation
- Changed name of RestartCommand to DeleteCommand and added [<Player>] 
- Changed name of PlanetCommandListener to PlanetExpandCommandListener 
- Fixed a KDoc in Atmosphere 
- Fixed planet visit command #11 
- Deleted debug messages in BaseLoadedPlanet.<init>
- Deleted debug messages in setCuboid 
- Deleted debug messages in canBuild 
- Fixed canBuild & Fixed planet (set)home command & Deleted debug messages LartyHD 
- Added event canceling to RestartCommandListener
- Changed default atmosphere type to Cuboid 
- Improved code and added Planet Visit #11 
- Added lombok for Java classes 
- Removed lombok 
- Fixed outer region cleaning 
- Added [only-players] to list command 
- Fixed maxSize bug
- Added inventory clearing on restart command
- Fixed a restart command message 
- Start size of TopCommand by 1 
- Fixed planet home rounding 
- Fixed lombok & a NullPointerException 
- Fixed gradle and some imports 
- Make the database connection configurable #157
- Fixed a Atmosphere Database bug 
- add command member count permissions (Planet Members (Permission) #51) 
  
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
