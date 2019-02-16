package de.astride.planetsystem.api.database

import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.Planet
import java.util.*

interface DatabasePlanet {

	val uuid: UUID
	val name: String
	val ownerUniqueId: UUID
	val members: List<UUID>
	val size: Byte
	val planetLocation: PlanetLocation
    val metaData: Map<String, Any>

	fun toPlanet(): Planet

}
