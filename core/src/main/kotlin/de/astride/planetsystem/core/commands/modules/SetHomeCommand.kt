/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_NOT_IN_HIS_PLANET
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_SET_HOME_SUCCESSES

class SetHomeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val planet = planetPlayer.planet
        val logger = planetPlayer.logger

        if (planetPlayer.isOnHisPlanet())
            logger.warn(COMMANDS_SET_HOME_NOT_IN_HIS_PLANET)
        else {
            planet.spawnLocation = PlanetLocation(planet, planetPlayer.player.location).apply {
                vector.x = vector.blockX + 0.5
                vector.z = vector.blockZ + 0.5
            }
            logger.info(COMMANDS_SET_HOME_SUCCESSES)
        }
    }

}
