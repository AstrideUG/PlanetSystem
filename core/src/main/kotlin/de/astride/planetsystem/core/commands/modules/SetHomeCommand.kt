package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.isNotInGameWorld
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_SUCCESSES

class SetHomeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val player = planetPlayer.player
        val planet = planetPlayer.planet
        val logger = planetPlayer.logger

        if (player.isNotInGameWorld())
            logger.warn(COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD)
        else {
            planet.spawnLocation = PlanetLocation(planet, player.location).apply {
                vector.x = vector.blockX + 0.5
                vector.z = vector.blockZ + 0.5
            }
            logger.info(COMMANDS_SET_HOME_SUCCESSES)
        }
    }

}
