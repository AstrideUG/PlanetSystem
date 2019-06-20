/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.functions.planet
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.relativeTo
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.entity.Player

interface PlanetPlayer {

    val player: Player
    val logger: Logger
    val planet: LoadedPlanet

    fun unload()
    fun save()

}

fun PlanetPlayer.canBuild(location: Location): Boolean {
    val owner = planet.owner
    val planet = location.planet ?: return false
    return planet.owner == owner || owner in planet.members
}

fun PlanetPlayer.isOnHisPlanet(): Boolean = planet.outer.isInside(relativeTo())
