package de.astride.planetsystem.api.log

import org.bukkit.entity.Player

interface PlayerLogger : Logger {

	val player: Player

}
