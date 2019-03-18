package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.listeners.teleportHome
import de.astride.planetsystem.core.log.MessageKeys
import org.bukkit.GameMode

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (15.02.2019 - 04.03.2019)
 */
class HomeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val target = planetPlayer.player
        target.gameMode = GameMode.SURVIVAL
        target.fallDistance = 0f
        planetPlayer.teleportHome()
        planetPlayer.logger.info(MessageKeys.COMMANDS_HOME_TELEPORTATION_SUCCESS)
    }

}
