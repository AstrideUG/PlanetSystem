package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.PlanetSystem

interface PlanetCommandModule {

    val usage: Array<String> get() = arrayOf(javaClass.simpleName.replace("Command", ""))

    fun execute(planetPlayer: PlanetPlayer, args: Array<String>)

    fun permissions(args: Array<String>): String = "${PlanetSystem::class.qualifiedName}.Commands.Planet.$usage"

}
