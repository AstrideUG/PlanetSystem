package me.devsnox.planetsystem.api.planet

import me.devsnox.planetsystem.api.location.PlanetLocation
import java.util.*

interface Planet {

	val uniqueID: UUID

	val ownerUniqueID: UUID

	val name: String

	val members: MutableList<UUID>

	/**
	 * Change size and if loaded inner region
	 */
	var size: Byte

	var spawnLocation: PlanetLocation

	fun load(result: (LoadedPlanet) -> Unit)

}
