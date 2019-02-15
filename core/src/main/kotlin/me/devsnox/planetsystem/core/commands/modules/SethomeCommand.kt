package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.holder.isNotInHolderWorld
import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule

class SethomeCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) = if (player.player.isNotInHolderWorld())
		player.logger.warn("commands.sethome.not_in_planet_world")
	else {
		player.planet.spawnLocation = PlanetLocation(player.planet, player.player.location)
		player.logger.info("commands.sethome.success")
	}

}
