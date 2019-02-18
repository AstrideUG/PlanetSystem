package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.OfflinePlanetPlayer
import de.astride.planetsystem.core.database.DatabasePlayer
import lombok.Data
import java.util.*

@Data
open class BaseOfflinePlanetPlayer(
    override val uuid: UUID,
    override val planet: Planet,
    override val memberedPlanets: List<Planet>
) : OfflinePlanetPlayer

fun BaseOfflinePlanetPlayer.toDatabasePlayer(): DatabasePlayer =
    DatabasePlayer(uuid, planet.uniqueID, memberedPlanets.map { it.uniqueID })
