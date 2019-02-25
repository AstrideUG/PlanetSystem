package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.OfflinePlanetPlayer
import de.astride.planetsystem.core.database.DatabasePlayer
import lombok.Data

@Data
open class BaseOfflinePlanetPlayer(
    override val owner: Owner,
    override val planet: Planet,
    override val memberedPlanets: List<Planet>
) : OfflinePlanetPlayer

fun BaseOfflinePlanetPlayer.toDatabasePlayer(): DatabasePlayer =
    DatabasePlayer(owner.uuid, planet.uniqueID.uuid, memberedPlanets.map { it.uniqueID.uuid })
