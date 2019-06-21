/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.functions.outerPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.planetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.listeners.teleportPlanetSpawn
import de.astride.planetsystem.core.log.MessageKeys.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID

/**
 * Created on 21.06.2019 01:02.
 * @author Lars Artmann | LartyHD
 */
object BanCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val logger = planetPlayer.logger
        if (args.size == 1) {
            val owner = Owner(args[0].toPlayerUUID())
            planetPlayer.planet.banned += owner
            logger.info(COMMANDS_BAN_TARGET_BANED)

            val targetPlanetPlayer = owner.planetPlayer ?: return
            targetPlanetPlayer.logger.info(COMMANDS_BAN_YOU_ARE_BANED)
            if (targetPlanetPlayer.player.location.outerPlanet != planetPlayer.planet) return
            if (!targetPlanetPlayer.teleportPlanetSpawn()) logger.warn(COMMANDS_BAN_TARGET_FAILED_KICKING)
        } else logger.warn(COMMANDS_BAN_FAILED_ARGS_SIZE_IS_NOT_1)
    }

}
