package de.astride.planetsystem.core.database

import com.mongodb.MongoClient
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import org.mongodb.morphia.Morphia

class DatabaseHandler : de.astride.planetsystem.api.handler.DatabaseHandler {

    private val planetDAO: PlanetDAO
    private val playerDAO: PlayerDAO

    init {

        val mongoClient = MongoClient("127.0.0.1", 27017) //TODO: Initialization of MongoClient

        val morphia = Morphia()
        val dataStore = morphia.createDatastore(mongoClient, "cosmic")
        dataStore.ensureIndexes()

        this.planetDAO = PlanetDAO(DatabasePlanet::class.java, dataStore)
        this.playerDAO = PlayerDAO(DatabasePlayer::class.java, dataStore)

    }

    override fun getPlanet(uuid: Owner): DatabasePlanet = this.planetDAO.findOne("owner", uuid)

    override fun savePlanet(databasePlanet: de.astride.planetsystem.api.database.DatabasePlanet) {
        this.planetDAO.save(databasePlanet as DatabasePlanet) //TODO FUCK YOU CAST!
    }

    override fun create(planet: UniqueID, player: Owner, result: (Boolean) -> Unit) {
        if (!this.playerDAO.exists("owner", player.uuid)) {

            val databasePlayer = DatabasePlayer(player.uuid, planet.uuid)
            this.savePlayer(databasePlayer)

            val databasePlanet = DatabasePlanet(
                planet.uuid,
                "Alpha Centauri" /*TODO: Random Name*/,
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
