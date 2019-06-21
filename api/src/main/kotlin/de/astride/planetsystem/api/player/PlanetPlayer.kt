/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.database.allMembers
import de.astride.planetsystem.api.functions.innerPlanet
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.relativeTo
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.proxies.Owner
import org.bukkit.Location
import org.bukkit.entity.Player

interface PlanetPlayer {

    val player: Player
    val logger: Logger
    val planet: LoadedPlanet

    fun unload()
    fun save()

}

val PlanetPlayer.owner get() = Owner(player.uniqueId)

fun PlanetPlayer.canEdit(location: Location): Boolean = owner in location.innerPlanet?.allMembers.orEmpty()

fun PlanetPlayer.isOnHisPlanet(): Boolean = planet.outer.isInside(relativeTo())
