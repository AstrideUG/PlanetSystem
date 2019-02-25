package de.astride.planetsystem.api.handler

import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID

interface DatabaseHandler {

    fun getPlanet(uuid: Owner): de.astride.planetsystem.api.database.DatabasePlanet

    fun savePlanet(databasePlanet: de.astride.planetsystem.api.database.DatabasePlanet)

    fun create(planet: UniqueID, player: Owner, result: (Boolean) -> Unit)

    fun getPlayer(uuid: Owner): DatabasePlayer

    fun savePlayer(databasePlayer: DatabasePlayer)

}
