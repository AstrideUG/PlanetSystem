package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule
import me.devsnox.planetsystem.core.log.MessageKeys.*

class VisitCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) = if (args.isNotEmpty())
		player.logger.warn(NOT_IMPLEMENTED) //TODO impl
	else player.logger.warn(COMMANDS_VISIT_FAILED_NO_ARGS)

}
