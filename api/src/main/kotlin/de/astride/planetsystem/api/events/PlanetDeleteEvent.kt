package de.astride.planetsystem.api.events

import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlanetDeleteEvent(val planet: LoadedPlanet) : Event() {

	override fun getHandlers(): HandlerList = handlerList

	companion object {

		@JvmStatic
		val handlerList = HandlerList()

	}

}
