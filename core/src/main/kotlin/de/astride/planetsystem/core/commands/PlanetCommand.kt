package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.commands.modules.expand.ExpandCommand
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlanetCommand(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    "Planet",
    usage = "[Home]" +
            "|[Info]" +
            "|[Invite]" +
            "|[Kick]" +
            "|[SetHome]" +
            "|[Visit]" +
            "|[Expand Info]" +
            "|[Expand Expand]" +
            "|[Expand Expand Chat]" +
            "|[Expand Expand Force]" +
            "|[Expand Shape]" +
            "|[Expand Shape Cube]" +
            "|[Expand Style]" +
            "|[Expand Style ID <ID>]" +
            "|[Expand Style SubID <ID>]",
    minLength = 1,
    maxLength = 3
) {

    private val commandModules: MutableMap<String, PlanetCommandModule> = HashMap()

    init {
        arrayOf(
            ExpandCommand(),
            HomeCommand(),
            InfoCommand(),
            InviteCommand(),
            KickCommand(),
            SetHomeCommand(),
            VisitCommand()
        ).forEach { command ->
            command.usage.forEach { commandModules[it.toLowerCase()] = command }
        }
    }

    override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer { player ->
        if (args[0].isModule()) {
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
