package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys

class InfoCommand : PlanetCommandModule {

    override fun execute(player: PlanetPlayer, args: Array<String>) = if (player.isOnHisPlanet())
        player.logger.success(MessageKeys.COMMANDS_INFO_SUCCESS)
    else
        player.logger.warn(MessageKeys.COMMANDS_INFO_FAILED_NOT_OWN_PLANET)
}
