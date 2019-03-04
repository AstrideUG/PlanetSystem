package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.commands.modules.expand.ExpandCommand
import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlanetCommand(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    ConfigService.instance.config.planetCommand,
    usage = "Home" +
            "|Info" +
            "|List <Loaded/Database>" +
            "|Top" +
            "|ShowInner" +
            "|Add <Spieler>" +
            "|Remove <Spieler>" +
            "|SetHome" +
            "|Visit <Spieler>" +
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
    aliases = *ConfigService.instance.config.planetCommandAliases
) {

    private val commandModules: MutableMap<String, PlanetCommandModule> = HashMap()

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
            val planetPlayer =
                Holder.instance.players.find { it.player == player } ?: return@isPlayer //TODO Add error message
            val command = commandModules[args[0].toLowerCase()] ?: return@isPlayer
            val droppedArgs = args.drop(1).toTypedArray()
            hasPermission(sender, command.permissions(droppedArgs)) {
                command.execute(planetPlayer, droppedArgs)
            }
        } else sendUseMessage(sender)/*planetPlayer.logger.warn(MessageKeys.COMMANDS_MAIN_HELP)*/
    }

    private fun String.isModule() = commandModules.containsKey(toLowerCase())

}
