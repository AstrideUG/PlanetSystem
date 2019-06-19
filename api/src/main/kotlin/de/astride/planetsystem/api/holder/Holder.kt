package de.astride.planetsystem.api.holder

import de.astride.planetsystem.api.handler.DatabaseHandler
import de.astride.planetsystem.api.handler.GridHandler
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.relativeTo
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.gameWorld
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.api.proxies.players
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

interface Holder {

    val databaseHandler: DatabaseHandler

    val gridHandler: GridHandler

    val players: MutableSet<PlanetPlayer>
    val loadedPlanets: MutableSet<LoadedPlanet>

    companion object {

        lateinit var instance: Holder

    }

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:30.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
fun saveAll() {
    loadedPlanets.forEach { it.save() }
    players.forEach { it.save() }
}

fun Entity.isNotInGameWorld() = world.isNotGameWorld()
fun World.isNotGameWorld() = this != gameWorld

fun Set<PlanetPlayer>.find(owner: Owner) = find { it.planet.owner == owner }
fun Set<LoadedPlanet>.find(owner: Owner) = find { it.owner == owner }
fun Set<LoadedPlanet>.find(id: UniqueID) = find { it.uniqueID == id }
fun Set<LoadedPlanet>.find(location: Location) = if (location.world.isNotGameWorld()) null else find {
    it.inner.isInside(location.toVector().relativeTo(it.middle.toVector()))
}