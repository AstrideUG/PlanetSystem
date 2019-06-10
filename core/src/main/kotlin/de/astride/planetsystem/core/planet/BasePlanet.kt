package de.astride.planetsystem.core.planet

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.proxies.gridHandler
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.core.functions.place
import lombok.Data
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory

@Data
open class BasePlanet(
    override val uniqueID: UniqueID,
    override val name: String,
    override val owner: Owner,
    override val members: MutableSet<Owner>,
    override var spawnLocation: PlanetLocation,
    override var atmosphere: Atmosphere,
    override val metaData: Map<String, Any>
) : Planet {

    override fun load(result: (LoadedPlanet) -> Unit) {
        val location = gridHandler.findEmptyLocation()

        val loadedPlanet = BaseLoadedPlanet(this, location)
        loadedPlanets += loadedPlanet

        DynamicNetworkFactory.dynamicNetworkAPI.getSchematic(uniqueID.uuid) { schematic ->
            schematic.paste(location.toWEWorld(), location.toWEVector())
            loadedPlanet.place()

            result(loadedPlanet)
        }

    }

}
