package de.astride.planetsystem.core.commands.modules.expand.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.commands.modules.expand.AtmosphereCommand
import de.astride.planetsystem.core.commands.modules.expand.sendUsage

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:04.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class ShapeCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        when {
            args.isEmpty() -> planetPlayer.player.openInventory(AtmosphereCommand.Inventories.INVENTORY_SHAPE)
            args.size == 1 && "Cube" == args.firstOrNull() /*TODO: ADD PERMS*/ -> {
                //                if (planet !is SpherePlanet) {
                //                    planetPlayer.sendConfigurableMessage("Planet.Command.Shape.Cube.IsNotSpherePlanet")
                //                    return
                //                } else {
                //TODO #83
                planetPlayer.player.sendMessage("${usage.first().capitalize()} Not implemented safely yet. Sorry :(")
                //                    planet.toCubePlanet()
                //TODO Add success message
                //                }
            }
            else -> planetPlayer.player.sendUsage()
        }
    }

}