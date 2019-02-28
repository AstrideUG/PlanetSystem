package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys
import org.bukkit.GameMode

class HomeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val target = planetPlayer.player
        target.gameMode = GameMode.SURVIVAL
        target.fallDistance = 0f
        target.teleport(planetPlayer.planet.spawnLocation.toBukkitLocation())
        planetPlayer.logger.info(MessageKeys.COMMANDS_HOME_TELEPORTATION_SUCCESS)
    }

}
