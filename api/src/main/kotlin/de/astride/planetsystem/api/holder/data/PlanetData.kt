package de.astride.planetsystem.api.holder.data

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import org.bukkit.Location

interface PlanetData {

    val loadedPlanets: MutableSet<LoadedPlanet>

    fun load(owner: Owner, request: (LoadedPlanet) -> Unit)

    fun save(owner: Owner)

    fun unload(owner: Owner)

    fun getLoadedPlanet(owner: Owner): LoadedPlanet? = loadedPlanets.find { it.owner == owner }

    fun getLoadedPlanet(id: UniqueID): LoadedPlanet? = loadedPlanets.find { it.uniqueID == id }

    fun getPlanet(location: Location): Planet? = loadedPlanets.find { it.inner.isInside(PlanetLocation(it, location)) }

}
