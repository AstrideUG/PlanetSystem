package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.data.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.commands.modules.expand.ExpandCommand
import de.astride.planetsystem.core.log.MessageKeys
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlanetCommand(javaPlugin: JavaPlugin) : Command(javaPlugin, "Planet") {

    private val commandModules: MutableMap<String, PlanetCommandModule> = HashMap()

    init {
        arrayOf(
            ExpandCommand(),
            HomeCommand(),
            InfoCommand(),
            InviteCommand(),
            KickCommand(),
            SetHomeCommand()
        ).forEach { command ->
            command.usage.forEach { commandModules[it.toLowerCase()] = command }
        }
    }

    override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer {
        val player = Holder.instance.playerData.find(Owner(it.uniqueId)) ?: return@isPlayer
        if (args[0].isModule()) {
            val command = commandModules[args[0].toLowerCase()] ?: return@isPlayer
            val droppedArgs = args.drop(1).toTypedArray()
            hasPermission(sender, command.permissions(droppedArgs)) {
                command.execute(player, droppedArgs)
            }
        } else player.logger.warn(MessageKeys.COMMANDS_MAIN_HELP)
    }

    private fun String.isModule() = commandModules.containsKey(toLowerCase())

}
