/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.databasePlayer
import de.astride.planetsystem.api.proxies.planet
import de.astride.planetsystem.api.proxies.planetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.log.MessageKeys
import de.astride.planetsystem.core.proxies.config
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID
import org.bukkit.entity.Player
import java.util.*

/**
 * Created on 14.03.2019 14:11.
 * @author Lars Artmann | LartyHD
 */
class DeleteCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val logger = planetPlayer.logger
        if (!planetPlayer.isOnHisPlanet() && (args.isEmpty() || args[0].equals(confirmedKey, true))) {
            logger.warn(MessageKeys.COMMANDS_DELETE_FAILED_NOT_OWN_PLANET)
            return
        }
        when {
            args.isEmpty() -> planetPlayer.player.openInventory(config.commands.restart.inventory)
            confirmedKey.equals(args.last(), true) -> {

                val target = planetPlayer.player.getTarget(logger, args) ?: return
                target.toPlayer()?.clear()

                val owner = Owner(target)
                owner.planet?.unload()
                owner.planetPlayer?.unload()

                owner.databasePlayer?.let { databaseHandler.deletePlayer(it) }

                //Don't delete the schematic

                logger.info(MessageKeys.COMMANDS_DELETE_SUCCESSES)
            }
            args.size == 1 -> logger.info(MessageKeys.COMMANDS_DELETE_OTHER_ADD_CONFIRMED)
            else -> {
                /* TODO Send usage */
            }
        }
    }

    private fun Player.getTarget(logger: Logger, args: Array<String>): UUID? {
        return if (args.size == 2) {
            if (!hasPermission("${permissions(args)}.other")) {
                logger.warn("no.perms")
                return null
            }
            args[0].toPlayerUUID() ?: return null
        } else this.uniqueId
    }

    private fun Player.clear() {
        kickPlayer(messages["Planet.Command.Planet.Restart.confirmed.kick"] ?: "Planet deleted")
        inventory.armorContents = null
        inventory.clear()
    }

    companion object {
        private const val confirmedKey: String = "confirmed"
    }

}
