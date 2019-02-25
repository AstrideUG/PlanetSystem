package de.astride.planetsystem.api.planet

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation

interface Planet {

    val uniqueID: UniqueID

    val owner: Owner

    val name: String

    val members: MutableList<Owner>

    /**
     * Change size and if loaded inner region
     */
    var size: Byte

    var spawnLocation: PlanetLocation

    //TODO: suspend fun load(): LoadedPlanet
    fun load(result: (LoadedPlanet) -> Unit)

}
