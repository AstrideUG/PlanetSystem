package de.astride.planetsystem.core.database

import com.mongodb.MongoClient
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.core.PlanetSystem
import xyz.morphia.Morphia
import xyz.morphia.mapping.DefaultCreator


class DatabaseHandler : de.astride.planetsystem.api.handler.DatabaseHandler {

    private val planetDAO: PlanetDAO
    private val playerDAO: PlayerDAO

    init {

        val mongoClient = MongoClient("127.0.0.1", 27017) //TODO: Initialization of MongoClient


        val morphia = Morphia()

        morphia.mapper.options.objectFactory = object : DefaultCreator() {
            override fun getClassLoaderForClass(): ClassLoader = PlanetSystem::class.java.classLoader
        }

        morphia.map(DatabasePlanet::class.java, DatabasePlayer::class.java)

        val dataStore = morphia.createDatastore(mongoClient, "cosmic")
//        dataStore.ensureIndexes()

        this.planetDAO = PlanetDAO(DatabasePlanet::class.java, dataStore)
        this.playerDAO = PlayerDAO(DatabasePlayer::class.java, dataStore)

    }

    override fun getPlanet(uuid: Owner): DatabasePlanet = this.planetDAO.findOne("owner", uuid.uuid)

    override fun savePlanet(databasePlanet: de.astride.planetsystem.api.database.DatabasePlanet) {
        this.planetDAO.save(databasePlanet as DatabasePlanet) //TODO FUCK YOU CAST!
    }

    override fun create(planet: UniqueID, player: Owner, result: (Boolean) -> Unit) {
        try {
            println(Class.forName("DBCollectionObjectFactory").constructors)
            println(Class.forName("DBCollectionObjectFactory").modifiers)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


        println(planetDAO)
        println(playerDAO)

        println(getPlanet(player))

        println(playerDAO.exists("uuid", player.uuid))

        println(planetDAO.count())
        println(playerDAO.count())


//        println(planetDAO.exists("owner", player.uuid))
//        println(playerDAO.exists("owner", player.uuid))

        if (!this.playerDAO.exists("uuid", player.uuid)) {

            val databasePlayer = DatabasePlayer(player.uuid, planet.uuid)
            this.savePlayer(databasePlayer)

            val databasePlanet = DatabasePlanet(
                planet.uuid,
                "Kepler-730 c" /*TODO: Random Name*/,
                player.uuid,
                mutableSetOf(),
                8.toByte(),
                PlanetLocation(planet)
            )
            this.savePlanet(databasePlanet)

            result(true)
        } else
            result(false)
    }

    override fun getPlayer(uuid: Owner): DatabasePlayer = this.playerDAO.findOne("owner", uuid)

    override fun savePlayer(databasePlayer: de.astride.planetsystem.api.database.DatabasePlayer) {
        this.playerDAO.save(databasePlayer as DatabasePlayer) //TODO FUCK YOU CAST!
    }

}
