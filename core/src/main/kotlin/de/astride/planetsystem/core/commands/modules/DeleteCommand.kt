package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.api.proxies.databaseHandler
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.log.MessageKeys
import de.astride.planetsystem.core.proxies.config
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toOfflinePlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID
import org.bukkit.entity.Player

/**
 * Created on 14.03.2019 14:11.
 * @author Lars Artmann | LartyHD
 */
class DeleteCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val logger = planetPlayer.logger
        if (!planetPlayer.isOnHisPlanet() && args.size != 2) {
            logger.warn(MessageKeys.COMMANDS_RESTART_FAILED_NOT_OWN_PLANET)
            return
        }
        when {
            args.isEmpty() -> planetPlayer.player.openInventory(config.commands.restart.inventory)
            "confirmed".equals(args.last(), true) -> {

                planetPlayer.player.clear(logger, args)
                databaseHandler.deletePlanet(BasicDatabasePlanet.by(planetPlayer.planet))
                //Don't delete the schematic

            }
            else -> {
                /* TODO Send usage */
            }
        }
    }

    private fun Player.clear(logger: Logger, args: Array<String>) {

        val target = if (args.size == 2) {
            if (!hasPermission("${permissions(args)}.other")) {
                logger.warn("no.perms")
                return
            }
            args[1].toPlayerUUID()?.toOfflinePlayer()?.player ?: return
        } else this

        target.kickPlayer(
            messages["Planet.Command.Planet.Restart.confirmed.kick"] ?: "Planet deleted"
        )

        val inventory = target.inventory
        inventory.armorContents = null
        inventory.clear()
    }

}
