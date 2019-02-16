package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.Holder.Impl.holder
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys.*
import org.bukkit.Bukkit
import java.util.*

class InviteCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		val logger = player.logger
		if (args.size == 1) {

			//TODO update in the new Darkness-Spigot version (String.toPlayerUUID())
			val uuid: UUID = try {
				UUID.fromString(args[0])
			} catch (ex: Exception) {
				val target = Bukkit.getPlayer(args[0])
				if (target != null) target.uniqueId else {
					logger.warn(COMMANDS_INVITE_FAILED_IS_NO_UUID_AND_TARGET_IS_NOT_ONLINE)
					return
				}
			}

			if (uuid !in player.planet.members) {
				player.planet.members += uuid

				logger.success(COMMANDS_INVITE_SUCCESSES_PLAYER)
				holder.playerData.getPlayer(uuid)?.logger?.success(COMMANDS_INVITE_SUCCESSES_TARGET)
			} else logger.warn(COMMANDS_INVITE_FAILED_IS_ALREADY_A_PLANET_MEMBER)
		} else logger.warn(COMMANDS_INVITE_FAILED_ARGS_SIZE_IS_NOT_1)
	}

}
