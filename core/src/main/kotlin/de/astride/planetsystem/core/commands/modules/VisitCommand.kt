package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_VISIT_FAILED_NO_ARGS
import de.astride.planetsystem.core.log.MessageKeys.NOT_IMPLEMENTED

class VisitCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) = if (args.isNotEmpty())
        planetPlayer.logger.warn(NOT_IMPLEMENTED) //TODO impl
    else planetPlayer.logger.warn(COMMANDS_VISIT_FAILED_NO_ARGS)

}
