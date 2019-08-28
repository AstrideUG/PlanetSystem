/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.gridHandler
import de.astride.planetsystem.api.holder.loadedPlanets
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.proxies.loadedPlanet
import de.astride.planetsystem.core.planet.BaseLoadedPlanet
import de.astride.planetsystem.core.utils.getSchematic

/*
 * Created on 20.06.2019 17:47.
 * @author Lars Artmann | LartyHD
 */

fun OfflinePlanet.load(): LoadedPlanet {

    if (this is LoadedPlanet) return this
    uniqueID.loadedPlanet?.let { return it }

    val location = gridHandler.findEmptyLocation()

    val loadedPlanet = BaseLoadedPlanet(this, location)
    loadedPlanets += loadedPlanet

    val schematic = uniqueID.uuid.getSchematic()

    schematic.paste(location.toWEWorld(), location.toWEVector())
    loadedPlanet.place()

    return loadedPlanet

}
