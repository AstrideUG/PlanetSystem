/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.functions

import de.astride.planetsystem.api.holder.loadedPlanets
import de.astride.planetsystem.api.holder.players
import de.astride.planetsystem.api.location.Region
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.relativeTo
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.proxies.gameWorld
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

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

val Location.innerPlanet get() = planet { it.inner }
val Location.outerPlanet get() = planet { it.outer }

private fun Location.planet(region: (LoadedPlanet) -> Region) = loadedPlanets.find {
    val middle = it.middle
    if (world == middle.world) region(it).isInside(relativeTo(middle).toVector()) else false
}
