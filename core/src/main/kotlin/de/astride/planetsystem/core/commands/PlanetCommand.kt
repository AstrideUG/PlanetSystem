package de.astride.planetsystem.core.commands

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.core.commands.modules.*
import de.astride.planetsystem.core.log.MessageKeys.*
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlanetCommand(javaPlugin: JavaPlugin) : Command(javaPlugin, "Planet") {

	private val commandModules: MutableMap<String, PlanetCommandModule> = HashMap()

	init {
		commandModules["home"] = HomeCommand()
		commandModules["info"] = InfoCommand()
		commandModules["invite"] = InviteCommand()
		commandModules["kick"] = KickCommand()
		commandModules["sethome"] = SethomeCommand()
	}

	override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer {
		val planetPlayer = Holder.Impl.holder.playerData.getPlayer(it.uniqueId) ?: return@isPlayer
		when {
			args.isEmpty() -> planetPlayer.logger.info(COMMANDS_ARGS_NO)
			commandModules.containsKey(args[0].toLowerCase()) ->
				commandModules[args[0].toLowerCase()]?.execute(planetPlayer, Arrays.copyOfRange(args, 1, args.size))
			else -> planetPlayer.logger.info(COMMANDS_ARGS)
		}
	}

}
