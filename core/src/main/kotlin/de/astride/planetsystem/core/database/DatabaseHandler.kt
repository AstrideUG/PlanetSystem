/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database

import com.mongodb.MongoClient
import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.functions.BukkitVector
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

        morphia.mapper.options.objectFactory = object : DefaultCreator() {
            override fun getClassLoaderForClass(): ClassLoader = PlanetSystem::class.java.classLoader
        }
        morphia.map(DatabasePlanet::class.java, DatabasePlayer::class.java)
        morphia.mapper.addMappedClass(DataAtmosphere::class.java)

        val dataStore = morphia.createDatastore(mongoClient, "cosmic")
        dataStore.ensureIndexes()

        planetDAO = PlanetDAO(BasicDatabasePlanet::class.java, dataStore)
        playerDAO = PlayerDAO(BasicDatabasePlayer::class.java, dataStore)

    }


    override fun findPlayer(owner: Owner): DatabasePlayer? = if (playerDAO.exists(playerOwnerKey, owner.uuid))
        playerDAO.findOne(playerOwnerKey, owner.uuid)
    else null

    override fun findPlanet(owner: Owner): DatabasePlanet? = if (planetDAO.exists(planetOwnerKey, owner.uuid))
        planetDAO.findOne(planetOwnerKey, owner.uuid)
    else null


    override fun findPlayerOrCreate(owner: Owner, planet: UniqueID): DatabasePlayer =
        (findPlayer(owner) ?: BasicDatabasePlayer(owner.uuid, planet.uuid)).also(::savePlayer)

    override fun findPlanetOrCreate(owner: Owner, planet: UniqueID): DatabasePlanet =
        (findPlanet(owner) ?: BasicDatabasePlanet(
            planet.uuid,
            owner.uuid,
            "Kepler-730 c", /*TODO: Random Name*/
            mutableSetOf(),
            PlanetLocation(planet, vector = BukkitVector(0.5, 0.0, 0.5)),
            DataAtmosphere().checkedSize(),
            false,
            mutableMapOf()
        )).also(::savePlanet)


    override fun savePlayer(databasePlayer: DatabasePlayer) {
        playerDAO.save(databasePlayer as BasicDatabasePlayer)
    }

    override fun savePlanet(databasePlanet: DatabasePlanet) {
        planetDAO.save(databasePlanet as BasicDatabasePlanet)
    }


    override fun deletePlayer(databasePlayer: DatabasePlayer) {
        playerDAO.delete(databasePlayer as BasicDatabasePlayer)
    }

    override fun deletePlanet(databasePlanet: DatabasePlanet) {
        planetDAO.delete(databasePlanet as BasicDatabasePlanet)
    }

    companion object {
        private val playerOwnerKey: String = DatabasePlayer::uuid.name
        private val planetOwnerKey: String = DatabasePlanet::owner.name
    }

}
