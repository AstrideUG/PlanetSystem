package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.location.isInside
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule

class InfoCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		if (player.planet.inner.isInside(player.location)) {
			player.logger.success("commands.info.success")
		} else {
			player.logger.warn("commands.info.failed.not_own_planet")
		}
	}
}
