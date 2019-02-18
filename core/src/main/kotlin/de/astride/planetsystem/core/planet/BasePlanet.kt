package de.astride.planetsystem.core.planet

import de.astride.planetsystem.api.events.PlanetCreatedEvent
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.Holder.Impl.holder
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import lombok.Data
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import java.util.*

@Data
open class BasePlanet(
    override val uniqueID: UUID,
    override val name: String,
    override val ownerUniqueID: UUID,
    override val members: MutableList<UUID>,
    override var size: Byte,
    override var spawnLocation: PlanetLocation
) : Planet {

    override fun load(result: (LoadedPlanet) -> Unit) {
        val grid = holder.gridHandler
        val location = grid.findEmptyLocation()

        val loadedPlanet = BaseLoadedPlanet(this, location, grid.maxSize)
        holder.planetData.loadedPlanets.add(loadedPlanet)

        DynamicNetworkFactory.dynamicNetworkAPI.getSchematic(this.uniqueID) { schematic ->

            schematic.paste(location.toWEWorld(), location.toWEVector())

            PlanetCreatedEvent(loadedPlanet).call()

            result(loadedPlanet)
        }

    }


}
