/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.loadedPlanets
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.commands.modules.expand.sendUsage
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.functions.replaceKeys
import de.astride.planetsystem.core.functions.replacePlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendIfNotNull
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.ChatColor.*

/**
 * Created on 28.02.2019 07:58.
 * @author Lars Artmann | LartyHD
 */
class ListCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val sender = planetPlayer.player
        if (args.isNotEmpty()) {
            val prefix =
                "$AQUA${BOLD}Planets ${WHITE}┃$RESET $GREEN"

            var onlyPlayers = false

            if (args.size == 2)
                if (args[1].equals("only-players", true)) onlyPlayers = true else {
                    //TODO send usage
                    return
                }

            val onlyPlayersMessage = "$WHITE<Owner.UUID> $GRAY($WHITE<Owner.Name>$GRAY) is the owner of $WHITE<UUID>"
            when {
                "loaded".equals(args[0], true) -> {
                    "${prefix}List of all loaded planets:".sendTo(sender)
                    loadedPlanets.forEach {
                        val message =
                            if (onlyPlayers) onlyPlayersMessage.placeholder(prefix, it) else it.giveMessage(prefix)
                        message.sendIfNotNull(sender)
                    }
                }
                "database".equals(args[0], true) -> {
                    "${prefix}List of all database planets:".sendTo(sender)
                    //TODO replace with databaseHandler.allPlayers
                    databaseHandler.allPlanets.forEach { planet ->
                        val message = if (onlyPlayers)
                            onlyPlayersMessage.placeholder(prefix, planet)
                        else planet.giveMessage(prefix)
                        message.sendIfNotNull(sender)
                    }
                }
                else -> sender.sendUsage()
            }
        } else sender.sendUsage()
    }

    private fun LoadedPlanet.giveMessage(prefix: String): String? =
        arrayOf(
            "Informationen über den Planeten von <Owner.Name>",
            "Owner: $WHITE<Owner.UUID>",
            "Planet:",
            "  ID: $WHITE<UUID>",
            "  Name: $WHITE<Name>",
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
            "Members: $WHITE<Members.Lined>",
            "Atmosphere:",
            "  <Atmosphere.Lined>"
        ).joinToString("\n")
            .replace("Middle", middle)
            .replace("Inner.Min", inner.min)
            .replace("Inner.Max", inner.max)
            .replace("Outer.Min", outer.min)
            .replace("Outer.Max", outer.max)
            .placeholder(prefix, this)

    private fun OfflinePlanet.giveMessage(prefix: String): String? = arrayOf(
        "Informationen über den Planeten von $WHITE<Owner.Name>",
        "Owner: $WHITE<Owner.UUID>",
        "Planet:",
        "  ID: $WHITE<UUID>",
        "  Name: $WHITE<Name>",
        "SpawnLocation:",
        "  <SpawnLocation.Lined>",
        "Members: $WHITE<Members.Lined>",
        "Atmosphere:",
        "  <Atmosphere.Lined>"
    ).joinToString("\n").placeholder(prefix, this)

    private fun String?.placeholder(prefix: String, planet: OfflinePlanet) = this
        .replacePlayer("Owner", planet.owner.uuid)
        .replace("<UUID>", planet.uniqueID.uuid)
        .replace("<Name>", planet.name)
        .replace("SpawnLocation", planet.spawnLocation)
        .replaceKeys("Members", planet.members.toList())
        .replace(atmosphere = planet.atmosphere)
        ?.lines()
        ?.joinToString("\n") { "$prefix$it" }

}