package me.devsnox.planetsystem.api.player

import me.devsnox.planetsystem.api.planet.Planet
import java.util.*

interface OfflinePlanetPlayer {

	val uuid: UUID

	val planet: Planet

	val memberedPlanets: List<Planet>

}
