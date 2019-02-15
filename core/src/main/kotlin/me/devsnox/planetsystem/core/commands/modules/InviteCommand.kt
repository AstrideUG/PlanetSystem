package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.holder.Holder
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule
import org.bukkit.Bukkit
import java.util.*

class InviteCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		val logger = player.logger
		val prefix = "Commands.Invite.Failed."
		if (args.size == 1) {
			val uuid: UUID = try {
				UUID.fromString(args[0])
			} catch (ex: Exception) {
				val target = Bukkit.getPlayer(args[0])
				if (target != null) target.uniqueId else {
					logger.warn("${prefix}IsNoUUIDAndTargetIsNotOnline")
					return
				}
			}

			if (uuid !in player.planet.members) {
				player.planet.members.add(uuid)
				logger.success("Commands.Invite.Player.Successes")
				Holder.Impl.holder.playerData.getPlayer(player.uuid)!!.logger.success("Commands.Invite.Target.Successes")
			} else logger.warn("${prefix}IsAlreadyAPlanetMember")
		} else logger.warn("${prefix}ArgsSizeIsNot1")
	}

}
