package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.planet.Planet
import java.util.*

interface OfflinePlanetPlayer {

	val uuid: UUID

	val planet: Planet

	val memberedPlanets: List<Planet>

}
