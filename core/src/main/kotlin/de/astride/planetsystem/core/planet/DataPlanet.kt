/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.planet

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.Planet

data class DataPlanet(
    override val uniqueID: UniqueID,
    override val name: String,
    override val owner: Owner,
    override val members: MutableSet<Owner>,
    override var spawnLocation: PlanetLocation,
    override var atmosphere: Atmosphere,
    override var locked: Boolean,
    override val metaData: Map<String, Any>
) : Planet