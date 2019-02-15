package me.devsnox.planetsystem.core.listeners

import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.api.holder.isNotInHolderWorld
import me.devsnox.planetsystem.core.log.MessageKeys
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class PlanetListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

	@EventHandler(priority = EventPriority.LOW)
	fun onBlockPlaceEvent(event: BlockPlaceEvent) = blockBuild(event, event.block, event.player)

	@EventHandler(priority = EventPriority.LOW)
	fun onBlockBreakEvent(event: BlockBreakEvent) = blockBuild(event, event.block, event.player)

	@Suppress("SimplifyNegatedBinaryExpression")
	@EventHandler
	fun onPlayerInteractEvent(event: PlayerInteractEvent) {
		if (event.player.isNotInHolderWorld()) return
		if (event.action != Action.PHYSICAL) return
		if (!(event.clickedBlock?.type == Material.SOIL)) return

		cancel(event)
		event.setUseInteractedBlock(Event.Result.DENY)
	}

	private fun blockBuild(cancellable: Cancellable, block: Block, player: Player) {
		if (player.isNotInHolderWorld()) return

		val planetPlayer = holder.playerData.getPlayer(player.uniqueId) ?: return
		if (planetPlayer.canBuild(block)) return

		cancel(cancellable)
		planetPlayer.logger.warn(MessageKeys.LISTENER_PLANET_BUILD_DENY)
	}

}
