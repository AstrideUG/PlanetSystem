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
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory

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


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasePlanet) return false

        if (uniqueID != other.uniqueID) return false
        if (name != other.name) return false
        if (owner != other.owner) return false
        if (members != other.members) return false
        if (spawnLocation != other.spawnLocation) return false
        if (atmosphere != other.atmosphere) return false
        if (metaData != other.metaData) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uniqueID.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + members.hashCode()
        result = 31 * result + spawnLocation.hashCode()
        result = 31 * result + atmosphere.hashCode()
        result = 31 * result + metaData.hashCode()
        return result
    }

    override fun toString(): String =
        "BasePlanet(uniqueID=$uniqueID, name='$name', owner=$owner, members=$members, spawnLocation=$spawnLocation, atmosphere=$atmosphere, metaData=$metaData)"

}
