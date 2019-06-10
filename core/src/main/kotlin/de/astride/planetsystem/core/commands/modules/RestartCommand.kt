package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.log.MessageKeys
import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 14.03.2019 14:11.
 * Current Version: 1.0 (14.03.2019 - 18.03.2019)
 */
class RestartCommand : PlanetCommandModule {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 14.03.2019 14:11.
     * Current Version: 1.0 (14.03.2019 - 18.03.2019)
     */
    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) = if (planetPlayer.isOnHisPlanet()) {
        when {
            args.isEmpty() -> planetPlayer.player.openInventory(ConfigService.instance.config.commands.restart.inventory)
            args.size == 1 && "confirmed".equals(args[0], true) -> {

                planetPlayer.player.kickPlayer(
                    messages["Planet.Command.Planet.Restart.confirmed.kick"] ?: "Planet deleted"
                )
                Holder.instance.databaseHandler.deletePlanet(BasicDatabasePlanet.by(planetPlayer.planet))
                //Don't delete the schematic

            }
            else -> {
                /* TODO Send usage */
            }
        }

        planetPlayer.logger.success(MessageKeys.COMMANDS_INFO_SUCCESS)
    } else planetPlayer.logger.warn(MessageKeys.COMMANDS_RESTART_FAILED_NOT_OWN_PLANET)

}
