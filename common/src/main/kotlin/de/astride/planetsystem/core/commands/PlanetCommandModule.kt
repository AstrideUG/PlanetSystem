package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.player.PlanetPlayer

interface PlanetCommandModule {

	fun execute(player: PlanetPlayer, args: Array<String>)

}
