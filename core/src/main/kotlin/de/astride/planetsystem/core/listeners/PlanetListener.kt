package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.holder.isNotInHolderWorld
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.canBuild
import de.astride.planetsystem.core.log.MessageKeys
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
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

@Suppress("unused")
class PlanetListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockPlaceEvent(event: BlockPlaceEvent) = blockBuild(event, event.block, event.player)

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockBreakEvent(event: BlockBreakEvent) = blockBuild(event, event.block, event.player)

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.player.isNotInHolderWorld()) return
        if (event.action != Action.PHYSICAL) return
        val material = event.clickedBlock?.type ?: return
        if (material == Material.SOIL) return

        event.cancel()
        event.setUseInteractedBlock(Event.Result.DENY)
    }

}

private fun blockBuild(cancellable: Cancellable, block: Block, player: Player) {
    if (player.isNotInHolderWorld()) return

    val planetPlayer = Holder.instance.players.find(Owner(player.uniqueId)) ?: return
    if (planetPlayer.canBuild(block)) return

    cancellable.cancel()
    planetPlayer.logger.warn(MessageKeys.LISTENER_PLANET_BUILD_DENY)
}

