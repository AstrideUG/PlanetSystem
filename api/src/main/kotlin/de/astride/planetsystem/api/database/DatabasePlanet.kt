package de.astride.planetsystem.api.database

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation

interface DatabasePlanet {

    val uuid: UniqueID
    val name: String
    val ownerUniqueId: Owner
    val members: Set<Owner>
    val atmosphere: Atmosphere
    val planetLocation: PlanetLocation
    val metaData: Map<String, Any>

}
