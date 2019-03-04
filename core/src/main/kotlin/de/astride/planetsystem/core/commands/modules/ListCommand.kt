package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:58.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class ListCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
//        planetPlayer.planetPlayer.sendMessage("Alle Planeten:")
//        Planet.planets.forEach { planetPlayer.planetPlayer.sendMessage(it.toString()) }
        planetPlayer.player.sendMessage("${usage.first().capitalize()} not implemented yet. Sorry :(")
    }

}