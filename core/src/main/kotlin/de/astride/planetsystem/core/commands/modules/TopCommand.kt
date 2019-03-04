package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:54.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class TopCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
//        planetPlayer.sendConfigurableMessage("Planet.Command.TopCommand.Loading")
        planetPlayer.player.sendMessage("${usage.first().capitalize()} not implemented yet. Sorry :(")
    }

}