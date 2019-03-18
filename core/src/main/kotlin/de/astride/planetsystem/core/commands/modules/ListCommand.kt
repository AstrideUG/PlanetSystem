package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.databaseHandler
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.commands.modules.expand.sendUsage
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.functions.replaceKeys
import de.astride.planetsystem.core.functions.replacePlayer
import de.astride.planetsystem.core.functions.toPlanet
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendIfNotNull
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:58.
 * Current Version: 1.0 (28.02.2019 - 18.03.2019)
 */
class ListCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        if (args.isNotEmpty()) {
            val prefix =
                "${ChatColor.AQUA}${ChatColor.BOLD}Planets ${ChatColor.WHITE}┃${ChatColor.RESET} ${ChatColor.GREEN}"

            when {
                "loaded".equals(args[0], true) -> {
                    "${prefix}List of all loaded planets:".sendTo(planetPlayer.player)
                    loadedPlanets.forEach { it.send(prefix, planetPlayer.player) }
                }
                "database".equals(args[0], true) -> {
                    "${prefix}List of all database planets:".sendTo(planetPlayer.player)
                    databaseHandler.allPlanets.forEach {
                        send(prefix, it.toPlanet(), planetPlayer.player)
                    }
                }
                else -> planetPlayer.player.sendUsage()
            }
        } else planetPlayer.player.sendUsage()
    }

    private fun LoadedPlanet.send(prefix: String, sender: CommandSender) = arrayOf(
        "Informationen über den Planeten von <Owner.Name>",
        "Owner: ${ChatColor.WHITE}<Owner.UUID>",
        "Planet:",
        "  ID: ${ChatColor.WHITE}<UUID>",
        "  Name: ${ChatColor.WHITE}<Name>",
        "Region Inner Min:",
        "  <Inner.Min.Lined>",
        "Region Inner Max:",
        "  <Inner.Max.Lined>",
        "Region Outer Min:",
        "  <Outer.Min.Lined>",
        "Region Outer Max:",
        "  <Outer.Max.Lined>",
        "SpawnLocation:",
        "  <SpawnLocation.Lined>",
        "Members: ${ChatColor.WHITE}<Members.Lined>",
        "Atmosphere:",
        "  <Atmosphere.Lined>"
    ).joinToString("\n")
        .replace("Middle", middle)
        .replace("Inner.Min", inner.min)
        .replace("Inner.Max", inner.max)
        .replace("Outer.Min", outer.min)
        .replace("Outer.Max", outer.max)
        .placeholder(prefix, this)
        .sendIfNotNull(sender)

    private fun send(prefix: String, planet: Planet, sender: CommandSender) = arrayOf(
        "Informationen über den Planeten von ${ChatColor.WHITE}<Owner.Name>",
        "Owner: ${ChatColor.WHITE}<Owner.UUID>",
        "Planet:",
        "  ID: ${ChatColor.WHITE}<UUID>",
        "  Name: ${ChatColor.WHITE}<Name>",
        "SpawnLocation:",
        "  <SpawnLocation.Lined>",
        "Members: ${ChatColor.WHITE}<Members.Lined>",
        "Atmosphere:",
        "  <Atmosphere.Lined>"
    ).joinToString("\n").placeholder(prefix, planet).sendIfNotNull(sender)

    private fun String?.placeholder(prefix: String, planet: Planet) = this
        .replacePlayer("Owner", planet.owner.uuid)
        .replace("<UUID>", planet.uniqueID.uuid)
        .replace("<Name>", planet.name)
        .replace("SpawnLocation", planet.spawnLocation)
        .replaceKeys("Members", planet.members)
        .replace(atmosphere = planet.atmosphere)
        ?.lines()
        ?.joinToString("\n") { "$prefix$it" }

}