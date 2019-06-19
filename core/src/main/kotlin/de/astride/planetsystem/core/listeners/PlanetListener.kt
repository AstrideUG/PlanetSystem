package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.holder.isNotInGameWorld
import de.astride.planetsystem.api.holder.saveAll
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.canBuild
import de.astride.planetsystem.api.proxies.players
import de.astride.planetsystem.core.flags.Flags
import de.astride.planetsystem.core.log.MessageKeys
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.plugin.java.JavaPlugin


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (15.02.2019 - 18.03.2019)
 */
@Suppress("unused")
class PlanetListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun on(event: EntitySpawnEvent) {
        if (Flags.Mobs.types.any { it == event.entityType } && !Flags.Mobs.value) event.cancel()
        if (Flags.Animals.types.any { it == event.entityType } && !Flags.Animals.value) event.cancel()
    }

    @EventHandler
    fun onBlockPlaceEvent(event: BlockPlaceEvent) = blockBuild(event, event.block, event.player)

    @EventHandler
    fun onBlockBreakEvent(event: BlockBreakEvent) = blockBuild(event, event.block, event.player)

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.player.isNotInGameWorld()) return
        if (event.action != Action.PHYSICAL) return
        val material = event.clickedBlock?.type ?: return
        if (material != Material.SOIL) return

        event.cancel()
        event.setUseInteractedBlock(Event.Result.DENY)
    }

    @EventHandler
    fun on(event: PluginDisableEvent) {
        if (event.plugin.name != "FastAsyncWorldEdit") return
        saveAll()
    }

}

private fun blockBuild(cancellable: Cancellable, block: Block, player: Player) {
    if (player.isNotInGameWorld()) return

    val planetPlayer = players.find(Owner(player.uniqueId)) ?: return
    if (planetPlayer.canBuild(block)) return

    cancellable.cancel()
    planetPlayer.logger.warn(MessageKeys.LISTENER_PLANET_BUILD_DENY)
}

