package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.players
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (15.02.2019 - 18.03.2019)
 */
class RemoveCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val logger = planetPlayer.logger
        if (args.size == 1) {
            val owner = Owner(args[0].toPlayerUUID())
            if (owner in planetPlayer.planet.members) {
                planetPlayer.planet.members -= owner

                logger.success(COMMANDS_KICK_SUCCESSES_PLAYER)
                players.find(owner)?.logger?.success(COMMANDS_KICK_SUCCESSES_TARGET)
            } else logger.warn(COMMANDS_KICK_FAILED_IS_NOT_A_PLANET_MEMBER)
        } else logger.warn(COMMANDS_KICK_FAILED_ARGS_SIZE_IS_NOT_1)
    }

}
