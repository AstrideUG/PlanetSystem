package me.devsnox.planetsystem.api.events

import me.devsnox.planetsystem.api.planet.LoadedPlanet
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlanetCreatedEvent(val planet: LoadedPlanet) : Event() {

	override fun getHandlers(): HandlerList = handlerList

	companion object {

		@JvmStatic
		val handlerList = HandlerList()

	}

}
