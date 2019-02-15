package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.holder.Holder
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule
import org.bukkit.Bukkit
import java.util.*

class KickCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		val logger = player.logger
		val prefix = "Commands.Kick.Failed."
		if (args.size == 1) {

			val uuid: UUID = try {
				UUID.fromString(args[0])
			} catch (ex: Exception) {
				val target = Bukkit.getPlayer(args[0])
				if (target != null) target.uniqueId else {
					logger.warn("${prefix}InNoUUIDAndTargetIsNotOnline")
					return
				}
			}

			if (player.planet.members.contains(uuid)) {
				player.planet.members.remove(uuid)
				logger.success("Commands.Kick.Player.Successes")
				Holder.Impl.holder.playerData.getPlayer(player.uuid)!!.logger.success("Commands.Kick.Target.Successes")
			} else {
				logger.warn(prefix + "IsAlreadyAPlanetMember")
			}
		} else {
			logger.warn(prefix + "ArgsSizeIsNot1")
		}
	}

}
