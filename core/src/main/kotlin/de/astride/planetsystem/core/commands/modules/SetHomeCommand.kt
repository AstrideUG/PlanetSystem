package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.isNotInHolderWorld
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_SUCCESSES

class SetHomeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) =
        if (planetPlayer.player.isNotInHolderWorld())
            planetPlayer.logger.warn(COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD)
        else {
            planetPlayer.planet.spawnLocation = PlanetLocation(planetPlayer.planet, planetPlayer.player.location)
            planetPlayer.logger.info(COMMANDS_SET_HOME_SUCCESSES)
        }

}
