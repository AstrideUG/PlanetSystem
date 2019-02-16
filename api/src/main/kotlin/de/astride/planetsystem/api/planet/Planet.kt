package de.astride.planetsystem.api.planet

import de.astride.planetsystem.api.location.PlanetLocation
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

	val metaData: Map<String, Any>

	fun load(result: (LoadedPlanet) -> Unit)

}
