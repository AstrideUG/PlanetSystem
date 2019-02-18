package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys
import org.bukkit.GameMode

class HomeCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) {
		val target = player.player
		target.gameMode = GameMode.SURVIVAL
		target.fallDistance = 0f
		target.teleport(player.planet.spawnLocation.toBukkitLocation())
		player.logger.info(MessageKeys.COMMANDS_HOME_TELEPORTATION_SUCCESS)
	}
}
