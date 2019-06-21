/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.databasePlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.log.MessageKeys
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import org.bukkit.ChatColor

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:54.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class TopCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {

        if (args.size > 1) planetPlayer.logger.warn(MessageKeys.COMMANDS_TOP_FAILED_ARGS_SIZE_BIGGER_1)
        else {

            val size = args.getOrNull(0)?.toIntOrNull()?.plus(1) ?: 10

            planetPlayer.logger.info(
                MessageKeys.COMMANDS_TOP_SUCCESSES_SIZE_INFO.toString().replace("<size>", size.toString(), true)
            )

            databaseHandler.allPlayers.mapNotNull { it.planetUniqueId.databasePlanet }
                .sortedBy { it.atmosphere.size }
                .take(size)
                .asReversed()
                .withIndex()
                .forEach { (index, planet) ->
                    planetPlayer.logger.info(
                        MessageKeys.COMMANDS_TOP_SUCCESSES_SIZE_INFO.toString()
                            .replace("<place>", (index + 1).toString(), true)
                            .replace("<name>", Fetcher.getName(planet.owner.uuid) ?: "${ChatColor.ITALIC}Unknown", true)
                            .replace(atmosphere = planet.atmosphere)!!
                    )
                }
        }

    }

}