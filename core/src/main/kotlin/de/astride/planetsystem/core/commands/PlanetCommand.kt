package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.proxies.players
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.commands.modules.expand.ExpandCommand
import de.astride.planetsystem.core.proxies.config
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (15.02.2019 - 18.03.2019)
 */
class PlanetCommand(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    config.planetCommand,
    usage = "Home" +
            "|Info" +
            "|List <Loaded/Database>" +
            "|Top [Size]" +
            "|ShowInner" +
            "|Add <Player>" +
            "|Remove <Player>" +
            "|SetHome" +
            "|Visit <Player>" +
            "|Expand Info" +
            "|Expand Expand" +
            "|Expand Expand Chat" +
            "|Expand Expand Force" +
            "|Expand Shape" +
            "|Expand Shape Cube" +
            "|Expand Style" +
            "|Expand Style ID <ID>" +
            "|Expand Style SubID <ID>",
    minLength = 1,
    maxLength = 4,
    aliases = *config.planetCommandAliases
) {

    private val commandModules: MutableMap<String, PlanetCommandModule> = mutableMapOf()

    init {
        arrayOf(
            ExpandCommand(),
            HomeCommand(),
            InfoCommand(),
            ListCommand(),
            TopCommand(),
            AddCommand(),
            RemoveCommand(),
            SetHomeCommand(),
            ShowInnerCommand(),
            VisitCommand()
        ).forEach { command ->
            command.usage.forEach { commandModules[it.toLowerCase()] = command }
        }
    }

    override fun perform(sender: CommandSender, args: Array<String>) {
        if (args[0].isModule()) sender.isPlayer { player ->
            val planetPlayer = players.find { it.player == player } ?: return@isPlayer //TODO Add error message
            val command = commandModules[args[0].toLowerCase()] ?: return@isPlayer
            val droppedArgs = args.drop(1).toTypedArray()
            hasPermission(sender, command.permissions(droppedArgs)) {
                command.execute(planetPlayer, droppedArgs)
            }
        } else sendUseMessage(sender)/*planetPlayer.logger.warn(MessageKeys.COMMANDS_MAIN_HELP)*/
    }

    private fun String.isModule() = commandModules.containsKey(toLowerCase())

}
