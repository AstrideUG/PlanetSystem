/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.handler

import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.database.OfflinePlayer
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.UniqueID

interface DatabaseHandler {

    val allPlayers: Set<OfflinePlayer>
    val allPlanets: Set<OfflinePlanet>

    fun findPlayer(owner: Owner): OfflinePlayer?
    fun findPlanet(planet: UniqueID): OfflinePlanet?

    fun findPlayerOrCreate(owner: Owner, planet: UniqueID): OfflinePlayer
    fun findPlanetOrCreate(planet: UniqueID, owner: Owner): OfflinePlanet

    fun savePlayer(databasePlayer: OfflinePlayer)
    fun savePlanet(databasePlanet: OfflinePlanet)

//    fun deletePlayer(databasePlayer: OfflinePlayer)
//    fun deletePlanet(loadedPlanet: OfflinePlanet)

    fun deletePlanet(databasePlayer: OfflinePlayer)

}
