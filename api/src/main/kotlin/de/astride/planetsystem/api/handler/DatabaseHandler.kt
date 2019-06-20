/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.handler

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID

interface DatabaseHandler {

    val allPlanets: Set<DatabasePlanet>

    fun findPlayer(owner: Owner): DatabasePlayer?
    fun findPlanet(owner: Owner): DatabasePlanet?

    fun findPlayerOrCreate(owner: Owner, planet: UniqueID): DatabasePlayer
    fun findPlanetOrCreate(owner: Owner, planet: UniqueID): DatabasePlanet

    fun savePlayer(databasePlayer: DatabasePlayer)
    fun savePlanet(databasePlanet: DatabasePlanet)

    fun deletePlayer(databasePlayer: DatabasePlayer)
    fun deletePlanet(databasePlanet: DatabasePlanet)

}
