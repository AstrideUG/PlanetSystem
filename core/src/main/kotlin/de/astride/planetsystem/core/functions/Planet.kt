/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.gridHandler
import de.astride.planetsystem.api.holder.loadedPlanets
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.proxies.planet
import de.astride.planetsystem.core.planet.BaseLoadedPlanet
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory

/*
 * Created on 20.06.2019 17:47.
 * @author Lars Artmann | LartyHD
 */

//TODO: suspend fun load(): LoadedPlanet
fun Planet.load(result: (LoadedPlanet) -> Unit) {

    if (this is LoadedPlanet) {
        result(this)
        return
    }
    uniqueID.planet?.let {
        result(it)
        return
    }

    val location = gridHandler.findEmptyLocation()

    val loadedPlanet = BaseLoadedPlanet(this, location)
    loadedPlanets += loadedPlanet

    DynamicNetworkFactory.dynamicNetworkAPI.getSchematic(uniqueID.uuid) { schematic ->
        schematic.paste(location.toWEWorld(), location.toWEVector())
        loadedPlanet.place()

        result(loadedPlanet)
    }

}
