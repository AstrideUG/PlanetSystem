package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.relativeTo
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.proxies.loadedPlanets
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

interface PlanetPlayer {

    val player: Player
    val logger: Logger
    val planet: LoadedPlanet

    fun unload()

    fun save()

}

fun PlanetPlayer.canBuild(block: Block) = canBuild(block.location)
fun PlanetPlayer.canBuild(location: Location): Boolean {
    val planet = loadedPlanets.find(location) ?: return false
    val owner = this.planet.owner
    return planet.owner == owner || owner in planet.members
}

fun PlanetPlayer.isOnHisPlanet() = planet.inner.isInside(relativeTo())
