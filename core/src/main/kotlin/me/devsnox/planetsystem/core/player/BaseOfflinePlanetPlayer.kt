package me.devsnox.planetsystem.core.player

import lombok.Data
import me.devsnox.planetsystem.api.planet.Planet
import me.devsnox.planetsystem.api.player.OfflinePlanetPlayer
import me.devsnox.planetsystem.core.database.DatabasePlayer
import java.util.*

@Data
open class BaseOfflinePlanetPlayer(
		override val uuid: UUID,
		override val planet: Planet,
		override val memberedPlanets: List<Planet>
) : OfflinePlanetPlayer

fun BaseOfflinePlanetPlayer.toDatabasePlayer(): DatabasePlayer = DatabasePlayer(uuid, planet.uniqueID, memberedPlanets.map { it.uniqueID })
