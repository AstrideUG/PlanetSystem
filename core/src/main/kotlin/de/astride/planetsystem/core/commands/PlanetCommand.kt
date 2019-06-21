/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.planetPlayer
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.commands.modules.expand.AtmosphereCommand
import de.astride.planetsystem.core.proxies.config
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (15.02.2019 - 10.06.2019)
 */
class PlanetCommand(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    config.planetCommand,
    usage = "Info" +
            "|Home" +
            "|SetHome" +
            "|ShowInner" +
            "|FillInner" +
            "|Lock" +
            "|List Loaded/Database [only-players]" +
            "|Delete [<Player>]" +
            "|Top [<Size>]" +
            "|Visit <Player>" +
            "|Add <Player>" +
            "|Remove <Player>" +
            "|Kick <Player>" +
            "|Ban <Player>" +
            "|UnBan <Player>" +
            "|Atmosphere Info" +
            "|Atmosphere Expand [Chat/Force]" +
            "|Atmosphere Shape [Cube]" +
            "|Atmosphere Style [ID/SubID <ID>]",
    minLength = 1,
    maxLength = 4,
    aliases = *config.planetCommandAliases
) {

    private val commandModules: MutableMap<String, PlanetCommandModule> = mutableMapOf()

    init {
        arrayOf(
            AtmosphereCommand(),
            HomeCommand(),
            InfoCommand(),
            ListCommand(),
            DeleteCommand(),
            TopCommand(),
            AddCommand(),
            RemoveCommand(),
            SetHomeCommand(),
            ShowInnerCommand(),
            FillInnerCommand(),
            KickCommand(),
            BanCommand,
            UnBanCommand,
            LockCommand,
            VisitCommand()
        ).forEach { command ->
            command.usage.forEach { commandModules[it.toLowerCase()] = command }
        }
    }

    override fun perform(sender: CommandSender, args: Array<String>) {
        if (args[0].isModule()) sender.isPlayer { player ->
            val planetPlayer = Owner(player.uniqueId).planetPlayer ?: return@isPlayer //TODO Add error message
            val command = commandModules[args[0].toLowerCase()] ?: return@isPlayer
            val droppedArgs = args.drop(1).toTypedArray()
            hasPermission(sender, command.permissions(droppedArgs)) {
                command.execute(planetPlayer, droppedArgs)
            }
        } else sendUseMessage(sender)/*planetPlayer.logger.warn(MessageKeys.COMMANDS_MAIN_HELP)*/
    }

    private fun String.isModule() = commandModules.containsKey(toLowerCase())

}
