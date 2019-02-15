package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.holder.isNotInHolderWorld
import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule
import me.devsnox.planetsystem.core.log.MessageKeys.*

class SethomeCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) = if (player.player.isNotInHolderWorld())
		player.logger.warn(COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD)
	else {
		player.planet.spawnLocation = PlanetLocation(player.planet, player.player.location)
		player.logger.info(COMMANDS_SET_HOME_SUCCESSES)
	}

}
