package me.devsnox.planetsystem.api.holder.data

import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.location.isInside
import me.devsnox.planetsystem.api.planet.LoadedPlanet
import me.devsnox.planetsystem.api.planet.Planet
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
