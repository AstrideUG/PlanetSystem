package me.devsnox.planetsystem.api.log

import org.bukkit.entity.Player

interface PlayerLogger : Logger {

	val player: Player

}
