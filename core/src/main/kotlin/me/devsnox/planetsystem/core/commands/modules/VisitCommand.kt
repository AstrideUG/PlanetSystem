package me.devsnox.planetsystem.core.commands.modules

import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.commands.PlanetCommandModule

class VisitCommand : PlanetCommandModule {

	override fun execute(player: PlanetPlayer, args: Array<String>) = if (args.isNotEmpty())
		player.memberedPlanets.forEach { planet ->
			if (planet.name.equals(args[0], true)) {
				//TODO: Add content!
			}
		}
	else player.logger.warn("Commands.Visit.Failed.ArgsSizeIs0")

}
