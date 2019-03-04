package de.astride.planetsystem.api.holder

import de.astride.planetsystem.api.handler.DatabaseHandler
import de.astride.planetsystem.api.handler.GridHandler
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
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

fun Entity.isNotInGameWorld() = world.isNotGameWorld()
fun World.isNotGameWorld() = this != Holder.instance.gridHandler.world

fun MutableSet<PlanetPlayer>.find(owner: Owner) = find { it.owner == owner }
fun MutableSet<LoadedPlanet>.find(owner: Owner) = find { it.owner == owner }
fun MutableSet<LoadedPlanet>.find(id: UniqueID): LoadedPlanet? = find { it.uniqueID == id }
fun MutableSet<LoadedPlanet>.find(location: Location): LoadedPlanet? =
    if (location.world.isNotGameWorld()) null else find {
        it.inner.isInside(PlanetLocation(it, location))
    }