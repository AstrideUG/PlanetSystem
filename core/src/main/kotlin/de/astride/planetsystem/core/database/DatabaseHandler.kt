package de.astride.planetsystem.core.database

import com.mongodb.MongoClient
import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.core.PlanetSystem
import de.astride.planetsystem.core.atmosphere.DataAtmosphere
import de.astride.planetsystem.core.atmosphere.checkedSize
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.database.entities.BasicDatabasePlayer
import xyz.morphia.Morphia
import xyz.morphia.mapping.DefaultCreator


open class DatabaseHandler : de.astride.planetsystem.api.handler.DatabaseHandler {

    override val allPlanets: Set<BasicDatabasePlanet> get() = planetDAO.find().toSet()

    private val planetDAO: PlanetDAO
    private val playerDAO: PlayerDAO

    init {

        val mongoClient = MongoClient("127.0.0.1", 27017) //TODO: Initialization of MongoClient
        val morphia = Morphia()

//        morphia.mapper.options.objectFactory = object : DefaultCreator() {
//            override fun getClassLoaderForClass(): ClassLoader = PlanetSystem::class.java.classLoader
//        }

        morphia.mapper.options.objectFactory = object : DefaultCreator() {
            override fun getClassLoaderForClass(): ClassLoader = PlanetSystem::class.java.classLoader
        }
        morphia.map(DatabasePlanet::class.java, DatabasePlayer::class.java)
        morphia.mapper.addMappedClass(DataAtmosphere::class.java)

//        morphia.mapper.converters.addConverter(object : TypeConverter() {
//            override fun decode(
//                targetClass: Class<*>?,
//                fromDBObject: Any,
//                optionalExtraInfo: MappedField?
//            ): PlanetLocation {
//                fromDBObject as BasicDBObject
//
//                val id = UniqueID(fromDBObject["planetID"] as UUID)
//                val vector = fromDBObject["vector"] as Vector
//                val yaw = fromDBObject["yaw"] as Float
//                val pitch = fromDBObject["pitch"] as Float
//                return PlanetLocation(id, vector, yaw, pitch)
//            }
//        })

        val dataStore = morphia.createDatastore(mongoClient, "cosmic")
//        dataStore.ensureIndexes()

        this.planetDAO = PlanetDAO(BasicDatabasePlanet::class.java, dataStore)
        this.playerDAO = PlayerDAO(BasicDatabasePlayer::class.java, dataStore)

    }

    override fun savePlanet(databasePlanet: DatabasePlanet) {
        this.planetDAO.save(databasePlanet as BasicDatabasePlanet)
    }

    override fun getDatabasePlayer(planet: UniqueID, owner: Owner): DatabasePlayer {
        if (playerDAO.exists("uuid", owner.uuid)) return playerDAO.findOne("uuid", owner.uuid)

        val databasePlayer = BasicDatabasePlayer(owner.uuid, planet.uuid)
        savePlayer(databasePlayer)

        return databasePlayer
    }

    override fun getDatabasePlanet(planet: UniqueID, owner: Owner): DatabasePlanet {

        if (planetDAO.exists("owner", owner.uuid)) return planetDAO.findOne("owner", owner.uuid)

        val databasePlanet = BasicDatabasePlanet(
            planet.uuid,
            "Kepler-730 c" /*TODO: Random Name*/,
            owner.uuid,
            mutableSetOf(),
            DataAtmosphere().checkedSize(),
            PlanetLocation(planet),
            mutableMapOf()
        )
        savePlanet(databasePlanet)

        return databasePlanet
    }

    override fun savePlayer(databasePlayer: DatabasePlayer) {
        this.playerDAO.save(databasePlayer as BasicDatabasePlayer)
    }

}
