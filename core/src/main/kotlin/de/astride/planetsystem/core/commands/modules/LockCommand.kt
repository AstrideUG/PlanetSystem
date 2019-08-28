/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_LOCK_OFF
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_LOCK_ON

/**
 * Created on 20.06.2019 02:56.
 * @author Lars Artmann | LartyHD
 */
object LockCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val planet = planetPlayer.planet
        planet.locked = !planet.locked
        planetPlayer.logger.info(if (planet.locked) COMMANDS_LOCK_ON else COMMANDS_LOCK_OFF)
    }

}
