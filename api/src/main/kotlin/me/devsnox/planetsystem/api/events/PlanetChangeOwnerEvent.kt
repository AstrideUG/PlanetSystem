package me.devsnox.planetsystem.api.events

import me.devsnox.planetsystem.api.planet.LoadedPlanet
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

@Suppress("unused")
class PlanetChangeOwnerEvent(val newOwner: UUID, val planet: LoadedPlanet) : Event() {

	val oldOwner: UUID = planet.ownerUniqueID

	override fun getHandlers(): HandlerList = handlerList

	companion object {

		@JvmStatic
		val handlerList = HandlerList()

	}

}
