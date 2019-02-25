package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.player.PlanetPlayer

interface PlanetCommandModule {

    val usage: Array<String> get() = arrayOf(javaClass.canonicalName.replace("Command", ""))

    fun execute(player: PlanetPlayer, args: Array<String>)

}
