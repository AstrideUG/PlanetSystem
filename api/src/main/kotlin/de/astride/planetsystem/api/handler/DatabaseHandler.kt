package de.astride.planetsystem.api.handler

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import java.util.*

interface DatabaseHandler {

	fun getPlanet(uuid: UUID): de.astride.planetsystem.api.database.DatabasePlanet

	fun savePlanet(databasePlanet: de.astride.planetsystem.api.database.DatabasePlanet)

	fun create(planet: UUID, player: UUID, result: (Boolean) -> Unit)

	fun getPlayer(uuid: UUID): DatabasePlayer

	fun savePlayer(databasePlayer: DatabasePlayer)

}
