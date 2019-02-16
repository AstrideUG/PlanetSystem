package de.astride.planetsystem.core.player

import lombok.Data
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.OfflinePlanetPlayer
import de.astride.planetsystem.core.database.entities.BasicDatabasePlayer
import java.util.*

@Data
open class BaseOfflinePlanetPlayer(
		override val uuid: UUID,
		override val planet: Planet,
		override val memberedPlanets: List<Planet>
) : OfflinePlanetPlayer

fun BaseOfflinePlanetPlayer.toDatabasePlayer(): BasicDatabasePlayer = BasicDatabasePlayer(uuid, planet.uniqueID, memberedPlanets.map { it.uniqueID })
