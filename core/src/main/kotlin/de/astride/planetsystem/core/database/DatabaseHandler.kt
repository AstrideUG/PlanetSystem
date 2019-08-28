/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database

import com.mongodb.MongoClient
import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.database.OfflinePlayer
import de.astride.planetsystem.api.functions.BukkitVector
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.UniqueID
import de.astride.planetsystem.api.proxies.databasePlayer
import de.astride.planetsystem.api.proxies.planet
import de.astride.planetsystem.core.PlanetSystem
import de.astride.planetsystem.core.atmosphere.DataAtmosphere
import de.astride.planetsystem.core.atmosphere.checkedSize
import de.astride.planetsystem.core.database.entities.BasicOfflinePlanet
import de.astride.planetsystem.core.database.entities.BasicOfflinePlayer
import de.astride.planetsystem.core.functions.toBasicOfflinePlayer
import de.astride.planetsystem.core.proxies.DataUniqueID
import xyz.morphia.Morphia
import xyz.morphia.mapping.DefaultCreator
import java.util.*


open class DatabaseHandler : de.astride.planetsystem.api.handler.DatabaseHandler {

    override val allPlayers: Set<OfflinePlayer> get() = playerDAO.find().toSet()
    override val allPlanets: Set<OfflinePlanet> get() = planetDAO.find().toSet()

    private val planetDAO: PlanetDAO
    private val playerDAO: PlayerDAO

    init {

        val mongoClient = MongoClient("127.0.0.1", 27017) //TODO: Initialization of MongoClient
        val morphia = Morphia()

        morphia.mapper.options.objectFactory = object : DefaultCreator() {
            override fun getClassLoaderForClass(): ClassLoader = PlanetSystem::class.java.classLoader
        }
        morphia.map(OfflinePlanet::class.java, OfflinePlayer::class.java)
        morphia.mapper.addMappedClass(DataAtmosphere::class.java)
        morphia.mapper.addMappedClass(DataUniqueID::class.java)

        val dataStore = morphia.createDatastore(mongoClient, "cosmic")
        dataStore.ensureIndexes()

        planetDAO = PlanetDAO(BasicOfflinePlanet::class.java, dataStore)
        playerDAO = PlayerDAO(BasicOfflinePlayer::class.java, dataStore)

    }


    override fun findPlayer(owner: Owner): OfflinePlayer? = if (playerDAO.exists(playerOwnerKey, owner.uuid))
        playerDAO.findOne(playerOwnerKey, owner.uuid)
    else null

    override fun findPlanet(planet: UniqueID): OfflinePlanet? = if (planetDAO.exists(planetUniqueIDKey, planet.uuid))
        planetDAO.findOne(planetUniqueIDKey, planet.uuid)
    else null


    override fun findPlayerOrCreate(owner: Owner, planet: UniqueID): OfflinePlayer =
        (owner.databasePlayer ?: generatePlayer(owner, planet)).apply { savePlayer(toBasicOfflinePlayer()) }

    override fun findPlanetOrCreate(planet: UniqueID, owner: Owner): OfflinePlanet =
        (owner.planet ?: generatePlanet(planet, owner)).apply { savePlanet(toBasicOfflinePlayer()) }


    override fun savePlayer(databasePlayer: OfflinePlayer) {
        playerDAO.save(databasePlayer as BasicOfflinePlayer)
    }

    override fun savePlanet(databasePlanet: OfflinePlanet) {
        planetDAO.save(databasePlanet as BasicOfflinePlanet)
    }


//    override fun deletePlayer(databasePlayer: OfflinePlayer) {
//        playerDAO.delete(databasePlayer as BasicOfflinePlayer)
//    }
//
//    override fun deletePlanet(loadedPlanet: OfflinePlanet) {
//        planetDAO.delete(loadedPlanet as BasicOfflinePlanet)
//    }

    override fun deletePlanet(databasePlayer: OfflinePlayer) {
        val newPlayer = generatePlayer(
            databasePlayer.owner,
            history = (databasePlayer.history + databasePlayer.planetUniqueId).toMutableSet()
        )
        savePlayer(newPlayer)
    }

    private fun generatePlayer(
        owner: Owner,
        planet: UniqueID = DataUniqueID(UUID.randomUUID()),
        history: MutableSet<UniqueID> = mutableSetOf()
    ): BasicOfflinePlayer = BasicOfflinePlayer(owner, planet, history)

    private fun generatePlanet(planet: UniqueID, owner: Owner): BasicOfflinePlanet = BasicOfflinePlanet(
        planet,
        owner,
        "Kepler-730 c", /*TODO: Random Name*/
        mutableSetOf(),
        mutableSetOf(),
        PlanetLocation(planet, vector = BukkitVector(0.5, 0.0, 0.5)),
        DataAtmosphere().checkedSize(),
        false,
        mutableMapOf()
    )

    companion object {
        private val playerOwnerKey: String = OfflinePlayer::owner.name
        private val planetUniqueIDKey: String = OfflinePlanet::uniqueID.name
    }

}
