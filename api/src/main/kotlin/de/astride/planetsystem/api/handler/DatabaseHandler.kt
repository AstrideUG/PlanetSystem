package de.astride.planetsystem.api.handler

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID

interface DatabaseHandler {

    val allPlanets: Set<DatabasePlanet>


    fun getDatabasePlayer(planet: UniqueID, owner: Owner): DatabasePlayer
    fun getDatabasePlanet(planet: UniqueID, owner: Owner): DatabasePlanet

    fun savePlayer(databasePlayer: DatabasePlayer)
    fun savePlanet(databasePlanet: DatabasePlanet)

    fun deletePlanet(databasePlanet: DatabasePlanet)
}
