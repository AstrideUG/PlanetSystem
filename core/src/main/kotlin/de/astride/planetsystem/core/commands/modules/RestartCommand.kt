package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.api.proxies.databaseHandler
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.log.MessageKeys
import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 14.03.2019 14:11.
 * Current Version: 1.0 (14.03.2019 - 10.06.2019)
 */
class RestartCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        if (!planetPlayer.isOnHisPlanet()) {
            planetPlayer.logger.warn(MessageKeys.COMMANDS_RESTART_FAILED_NOT_OWN_PLANET)
            return
        }
        val player = planetPlayer.player
        when {
            args.isEmpty() -> {
                player.openInventory(ConfigService.instance.config.commands.restart.inventory)
                planetPlayer.logger.success(MessageKeys.COMMANDS_RESTART_SUCCESS)
            }
            args.size == 1 && "confirmed".equals(args[0], true) -> {

                val inventory = player.inventory
                inventory.armorContents = null
                inventory.clear()

                player.kickPlayer(
                    messages["Planet.Command.Planet.Restart.confirmed.kick"] ?: "Planet deleted"
                )
                databaseHandler.deletePlanet(BasicDatabasePlanet.by(planetPlayer.planet))
                //Don't delete the schematic

            }
            else -> {
                /* TODO Send usage */
            }
        }
    }

}
