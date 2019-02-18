package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.isNotInHolderWorld
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_SUCCESSES

class SethomeCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) = if (player.player.isNotInHolderWorld())
		player.logger.warn(COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD)
	else {
		player.planet.spawnLocation = PlanetLocation(player.planet, player.player.location)
		player.logger.info(COMMANDS_SET_HOME_SUCCESSES)
	}

}
