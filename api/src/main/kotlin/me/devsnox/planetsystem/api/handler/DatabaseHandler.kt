package me.devsnox.planetsystem.api.handler

import me.devsnox.planetsystem.api.database.DatabasePlanet
import me.devsnox.planetsystem.api.database.DatabasePlayer
import java.util.*

interface DatabaseHandler {

	fun getPlanet(uuid: UUID): DatabasePlanet

	fun savePlanet(databasePlanet: DatabasePlanet)

	fun create(planet: UUID, player: UUID, result: (Boolean) -> Unit)

	fun getPlayer(uuid: UUID): DatabasePlayer

	fun savePlayer(databasePlayer: DatabasePlayer)

}
