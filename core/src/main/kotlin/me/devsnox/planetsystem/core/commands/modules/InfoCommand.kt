package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.location.isInside
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule
import me.devsnox.planetsystem.core.log.MessageKeys

class InfoCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		if (player.planet.inner.isInside(player.location)) {
			player.logger.success(MessageKeys.COMMANDS_INFO_SUCCESS)
		} else {
			player.logger.warn(MessageKeys.COMMANDS_INFO_FAILED_NOT_OWN_PLANET)
		}
	}
}
