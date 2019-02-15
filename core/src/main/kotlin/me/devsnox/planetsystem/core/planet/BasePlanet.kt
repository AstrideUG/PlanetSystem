package me.devsnox.planetsystem.core.planet

import lombok.Data
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import me.devsnox.planetsystem.api.events.PlanetCreatedEvent
import me.devsnox.planetsystem.api.functions.toWEVector
import me.devsnox.planetsystem.api.functions.toWEWorld
import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.planet.LoadedPlanet
import me.devsnox.planetsystem.api.planet.Planet
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
