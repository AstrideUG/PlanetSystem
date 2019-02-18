package de.astride.planetsystem.core.log

import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.log.PlayerLogger
import lombok.Data
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

@Data
open class BasePlayerLogger(override val player: Player) : PlayerLogger {

	override fun log(level: Logger.Level, vararg message: Any) {
		val msg = Arrays.toString(message).substring(1, message.size - 1)

		when (level) {
			Logger.Level.INFO -> player.sendMessage("${ChatColor.AQUA}$msg.")
			Logger.Level.WARNING -> player.sendMessage("${ChatColor.RED}$msg!")
			Logger.Level.SUCCESSFULLY -> player.sendMessage("${ChatColor.GREEN}$msg.")
		}
	}

}
