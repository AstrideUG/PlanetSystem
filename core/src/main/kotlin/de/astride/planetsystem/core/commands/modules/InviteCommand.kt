package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID

class InviteCommand : PlanetCommandModule {

    override fun execute(player: PlanetPlayer, args: Array<String>) {
        val logger = player.logger
        if (args.size == 1) {
            val owner = Owner(args[0].toPlayerUUID())
            if (owner !in player.planet.members) {
                player.planet.members += owner

                logger.success(COMMANDS_INVITE_SUCCESSES_PLAYER)
                Holder.instance.playerData.getPlayer(owner)?.logger?.success(COMMANDS_INVITE_SUCCESSES_TARGET)
            } else logger.warn(COMMANDS_INVITE_FAILED_IS_ALREADY_A_PLANET_MEMBER)
        } else logger.warn(COMMANDS_INVITE_FAILED_ARGS_SIZE_IS_NOT_1)
    }

}
