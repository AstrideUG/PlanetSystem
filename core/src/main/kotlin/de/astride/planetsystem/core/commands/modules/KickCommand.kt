/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.functions.outerPlanet
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.planetPlayer
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.listeners.teleportPlanetSpawn
import de.astride.planetsystem.core.log.MessageKeys.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayer

/**
 * Created on 20.06.2019 02:17.
 * @author Lars Artmann | LartyHD
 */
class KickCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val logger = planetPlayer.logger
        if (args.size == 1) {
            val target = args[0].toPlayer()
            if (target != null) {
                if (target.location.outerPlanet != planetPlayer.planet) {
                    logger.warn(COMMANDS_KICK_TARGET_NOT_ON_YOUR_PLANET)
                    return
                }

                val targetPlanetPlayer = Owner(target.uniqueId).planetPlayer
                val teleportPlanetSpawn = targetPlanetPlayer?.teleportPlanetSpawn()
                if (teleportPlanetSpawn == true) {
                    logger.info(COMMANDS_KICK_TARGET_SUCCESSFULLY)
                    targetPlanetPlayer.logger.info(COMMANDS_KICK_YOU_WAS_KICKED)
                } else logger.warn(COMMANDS_KICK_TARGET_FAILED)
            } else logger.warn(COMMANDS_KICK_TARGET_IS_NOT_ONLINE)
        } else logger.warn(COMMANDS_KICK_FAILED_ARGS_SIZE_IS_NOT_1)
    }

}
