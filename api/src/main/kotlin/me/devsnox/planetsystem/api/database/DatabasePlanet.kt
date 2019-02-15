package me.devsnox.planetsystem.api.database

import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.planet.Planet
import java.util.*

interface DatabasePlanet {

	val uuid: UUID
	val name: String
	val ownerUniqueId: UUID
	val members: List<UUID>
	val size: Byte
	val planetLocation: PlanetLocation

	fun toPlanet(): Planet

}
