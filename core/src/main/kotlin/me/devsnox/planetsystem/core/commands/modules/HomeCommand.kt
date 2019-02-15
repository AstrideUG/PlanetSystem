package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.location.toBukkitLocation
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule
import org.bukkit.GameMode

class HomeCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		val target = player.player
		target.gameMode = GameMode.SURVIVAL
		target.fallDistance = 0f
		target.teleport(player.planet.spawnLocation.toBukkitLocation())
		player.logger.info("commands.home.teleport.success")
	}
}
