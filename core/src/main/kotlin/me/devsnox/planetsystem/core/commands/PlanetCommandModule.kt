package me.devsnox.planetsystem.core.commands

import me.devsnox.planetsystem.api.player.PlanetPlayer

interface PlanetCommandModule {

	fun execute(player: PlanetPlayer, args: Array<String>)

}
