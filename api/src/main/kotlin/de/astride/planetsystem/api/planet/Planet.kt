package de.astride.planetsystem.api.planet

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation

interface Planet {

    val uniqueID: UniqueID

    val owner: Owner

    val name: String

    val members: MutableList<Owner>

    var spawnLocation: PlanetLocation

    var atmosphere: Atmosphere

    val metaData: Map<String, Any>

    //TODO: suspend fun load(): LoadedPlanet
    fun load(result: (LoadedPlanet) -> Unit)

}
