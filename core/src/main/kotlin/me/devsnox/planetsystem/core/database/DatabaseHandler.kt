package me.devsnox.planetsystem.core.database

import com.mongodb.MongoClient
import me.devsnox.planetsystem.api.location.PlanetLocation
import org.mongodb.morphia.Morphia
import java.util.*

class DatabaseHandler : me.devsnox.planetsystem.api.handler.DatabaseHandler {

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

	override fun getPlanet(uuid: UUID): DatabasePlanet = this.planetDAO.findOne("owner", uuid)

	override fun savePlanet(databasePlanet: me.devsnox.planetsystem.api.database.DatabasePlanet) {
		this.planetDAO.save(databasePlanet as DatabasePlanet) //TODO FUCK YOU CAST!
	}

	override fun create(planet: UUID, player: UUID, result: (Boolean) -> Unit) {
		if (!this.playerDAO.exists("uuid", player)) {

			val databasePlayer = DatabasePlayer(player, planet, listOf())
			this.savePlayer(databasePlayer)

			val databasePlanet = DatabasePlanet(
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

	override fun getPlayer(uuid: UUID): DatabasePlayer = this.playerDAO.findOne("uuid", uuid)

	override fun savePlayer(databasePlayer: me.devsnox.planetsystem.api.database.DatabasePlayer) {
		this.playerDAO.save(databasePlayer as DatabasePlayer) //TODO FUCK YOU CAST!
	}

}
