package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_ARGS
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_ARGS_NO
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlanetCommand(javaPlugin: JavaPlugin) : Command(javaPlugin, "Planet") {

    private val commandModules: MutableMap<String, PlanetCommandModule> = HashMap()

    init {
        arrayOf(
//            ExpandCommand(),
            HomeCommand(),
            InfoCommand(),
            InviteCommand(),
            KickCommand(),
            SetHomeCommand()
        ).forEach { command ->
            command.usage.forEach { commandModules[it] = command }
        }
    }

    override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer {
        val planetPlayer = Holder.instance.playerData.getPlayer(Owner(it.uniqueId)) ?: return@isPlayer
        when {
            args.isEmpty() -> planetPlayer.logger.info(COMMANDS_ARGS_NO)
            commandModules.containsKey(args[0].toLowerCase()) ->
                commandModules[args[0].toLowerCase()]?.execute(planetPlayer, args.drop(1).toTypedArray())
            else -> planetPlayer.logger.info(COMMANDS_ARGS)
        }
    }

}
