package de.astride.planetsystem.api.database

import de.astride.planetsystem.api.location.PlanetLocation
import java.util.*

interface DatabasePlanet {

    val uuid: UUID
    val name: String
    val ownerUniqueId: UUID
    val members: Set<UUID>
    val size: Byte
    val planetLocation: PlanetLocation

}
