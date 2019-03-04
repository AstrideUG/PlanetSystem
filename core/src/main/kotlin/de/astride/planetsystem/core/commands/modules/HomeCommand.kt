package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.listeners.teleportHome
import de.astride.planetsystem.core.log.MessageKeys
import org.bukkit.GameMode

class HomeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val target = planetPlayer.player
        target.gameMode = GameMode.SURVIVAL
        target.fallDistance = 0f
        planetPlayer.teleportHome()
        planetPlayer.logger.info(MessageKeys.COMMANDS_HOME_TELEPORTATION_SUCCESS)
    }

}
