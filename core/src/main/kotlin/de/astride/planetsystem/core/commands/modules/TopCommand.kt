package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.toPlanet
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.ChatColor

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:54.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class TopCommand : PlanetCommandModule {

    private val holder get() = Holder.instance

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val prefix =
            "${ChatColor.AQUA}${ChatColor.BOLD}Planets ${ChatColor.WHITE}┃${ChatColor.RESET} ${ChatColor.GREEN}"
        "${prefix}Top #10".sendTo(planetPlayer.player)

        @Suppress("UNCHECKED_CAST")
        (holder.loadedPlanets as MutableSet<Planet>)
            .apply {
                holder.databaseHandler.allPlanets.map { it.toPlanet() }.forEach { planet ->
                    if (none { it.owner == planet.owner }) this.add(planet)
                }
            }
            .sortedBy { it.atmosphere.size }
            .asReversed()
            .take(10)
            .withIndex()
            .forEach { (index, planet) ->
                "$prefix#${index + 1}: ${ChatColor.WHITE}${Fetcher.getName(planet.owner.uuid)} (${planet.atmosphere.size})"
                    .sendTo(planetPlayer.player)
            }

        planetPlayer.player.sendMessage("${usage.first().capitalize()} not implemented yet. Sorry :(")
    }

}