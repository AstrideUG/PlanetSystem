package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.proxies.loadedPlanets
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

interface PlanetPlayer : OfflinePlanetPlayer {

    val player: Player
    val logger: Logger

    override val planet: LoadedPlanet
    override val owner: Owner get() = Owner(player.uniqueId)

    fun unload()

    fun save()

}

fun PlanetPlayer.canBuild(block: Block) = canBuild(block.location)
fun PlanetPlayer.canBuild(location: Location): Boolean {
    val planet = loadedPlanets.find(location) ?: return false
    return planet.owner == owner || owner in planet.members
}

fun PlanetPlayer.isOnHisPlanet() = planet.inner.isInside(this.toPlanetLocation())
fun PlanetPlayer.toPlanetLocation() = PlanetLocation(planet, player.location)
