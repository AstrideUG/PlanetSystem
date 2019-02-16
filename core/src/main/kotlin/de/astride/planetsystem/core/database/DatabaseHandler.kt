package de.astride.planetsystem.core.database

import com.mongodb.MongoClient
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.database.entities.BasicDatabasePlayer
import org.mongodb.morphia.Morphia
import java.util.*

class DatabaseHandler : de.astride.planetsystem.api.handler.DatabaseHandler {

	private val planetDAO: PlanetDAO
	private val playerDAO: PlayerDAO

	init {

		val mongoClient = MongoClient("127.0.0.1", 27017) //TODO: Initialization of MongoClient

		val morphia = Morphia()
		val dataStore = morphia.createDatastore(mongoClient, "cosmic")
		dataStore.ensureIndexes()

		this.planetDAO = PlanetDAO(BasicDatabasePlanet::class.java, dataStore)
		this.playerDAO = PlayerDAO(BasicDatabasePlayer::class.java, dataStore)

	}

	override fun getPlanet(uuid: UUID): BasicDatabasePlanet = this.planetDAO.findOne("owner", uuid)

	override fun savePlanet(databasePlanet: de.astride.planetsystem.api.database.DatabasePlanet) {
		this.planetDAO.save(databasePlanet as BasicDatabasePlanet) //TODO FUCK YOU CAST!
	}

	override fun create(planet: UUID, player: UUID, result: (Boolean) -> Unit) {
		if (!this.playerDAO.exists("uuid", player)) {

			val databasePlayer = BasicDatabasePlayer(player, planet, listOf())
			this.savePlayer(databasePlayer)

			val databasePlanet = BasicDatabasePlanet(
                    planet,
                    "Alpha Centauri" /*TODO: Random Name*/,
                    player,
                    ArrayList(),
                    8.toByte(),
                    PlanetLocation(planet)
            )
			this.savePlanet(databasePlanet)

			result(true)
		} else
			result(false)
	}

	override fun getPlayer(uuid: UUID): BasicDatabasePlayer = this.playerDAO.findOne("uuid", uuid)

	override fun savePlayer(databasePlayer: de.astride.planetsystem.api.database.DatabasePlayer) {
		this.playerDAO.save(databasePlayer as BasicDatabasePlayer) //TODO FUCK YOU CAST!
	}

}
