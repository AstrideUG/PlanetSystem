package de.astride.planetsystem.core.log

import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.log.PlayerLogger
import org.bukkit.ChatColor
import org.bukkit.entity.Player

open class BasePlayerLogger(override val player: Player) : PlayerLogger {

    override fun log(level: Logger.Level, vararg message: Any) {

        val msg = message.joinToString("") { it.toString() }

        when (level) {
            Logger.Level.INFO -> player.sendMessage("${ChatColor.AQUA}$msg.")
            Logger.Level.WARNING -> player.sendMessage("${ChatColor.RED}$msg!")
            Logger.Level.SUCCESSFULLY -> player.sendMessage("${ChatColor.GREEN}$msg.")
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasePlayerLogger) return false

        if (player != other.player) return false

        return true
    }

    override fun hashCode(): Int = player.hashCode()

    override fun toString(): String = "BasePlayerLogger(player=$player)"

}
