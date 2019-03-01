package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.Planet

interface OfflinePlanetPlayer {

    val owner: Owner

    val planet: Planet

    fun load(request: (PlanetPlayer) -> Unit)

}
