/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.database

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation

interface DatabasePlanet {

    val uniqueID: UniqueID
    val owner: Owner
    val name: String

    val members: MutableSet<Owner>

    val spawnLocation: PlanetLocation
    val atmosphere: Atmosphere

    val metaData: Map<String, Any>
    val locked: Boolean

}

val DatabasePlanet.allMembers get() = members + owner
