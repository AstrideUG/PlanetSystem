/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.planet

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.location.PlanetLocation

interface Planet : DatabasePlanet {

    override var spawnLocation: PlanetLocation
    override var atmosphere: Atmosphere
    override var locked: Boolean

    //TODO: suspend fun load(): LoadedPlanet
    fun load(result: (LoadedPlanet) -> Unit)

}
