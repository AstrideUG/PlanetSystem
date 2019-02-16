package de.astride.planetsystem.api.holder.data

import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import org.bukkit.Location
import java.util.*

interface PlanetData {

	val loadedPlanets: MutableSet<LoadedPlanet>

	fun load(owner: UUID, request: (LoadedPlanet) -> Unit)

	fun save(owner: UUID)

	fun unload(owner: UUID)

	fun getLoadedPlanetByOwner(owner: UUID): LoadedPlanet? = loadedPlanets.find { it.ownerUniqueID == owner }

	fun getLoadedPlanet(uuid: UUID): LoadedPlanet? = loadedPlanets.find { it.uniqueID == uuid }

	fun getPlanet(location: Location): Planet? = loadedPlanets.find { it.inner.isInside(PlanetLocation(it, location)) }

}
