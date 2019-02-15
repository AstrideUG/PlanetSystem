package me.devsnox.planetsystem.core.log

import lombok.Data
import me.devsnox.planetsystem.api.log.Logger
import me.devsnox.planetsystem.api.log.PlayerLogger
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

@Data
open class BasePlayerLogger(override val player: Player) : PlayerLogger {

	override fun log(level: Logger.Level, vararg message: Any) {
		val msg = Arrays.toString(message)

		when (level) {
			Logger.Level.INFO -> player.sendMessage("${ChatColor.AQUA}$msg.")
			Logger.Level.WARNING -> player.sendMessage("${ChatColor.RED}$msg!")
			Logger.Level.SUCCESSFULLY -> player.sendMessage("${ChatColor.GREEN}$msg.")
		}
	}

}
