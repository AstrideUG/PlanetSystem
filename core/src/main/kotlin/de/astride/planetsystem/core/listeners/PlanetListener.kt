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
import org.bukkit.entity.EntityType.*
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
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

    @EventHandler(priority = EventPriority.LOW)
    fun on(event: EntitySpawnEvent) {
//        if (!event.entityType.isAlive) return
        when (event.entityType) {
            CREEPER,
            SKELETON,
            SPIDER,
            GIANT,
            ZOMBIE,
            SLIME,
            GHAST,
            PIG_ZOMBIE,
            ENDERMAN,
            CAVE_SPIDER,
            SILVERFISH,
            BLAZE,
            MAGMA_CUBE,
            ENDER_DRAGON,
            WITHER,
            WITCH,
            ENDERMITE,
            GUARDIAN -> if (!Flags.Mobs.value) event.cancel()
            WOLF,
            PIG,
            SHEEP,
            COW,
            CHICKEN,
            SQUID,
            BAT,
            MUSHROOM_COW,
//            SNOWMAN,
            OCELOT,
//            IRON_GOLEM,
            HORSE,
            RABBIT,
            VILLAGER -> if (!Flags.Animals.value) event.cancel()
            else -> {
                /*IGNORE*/
            }
        }


    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockPlaceEvent(event: BlockPlaceEvent) = blockBuild(event, event.block, event.player)

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockBreakEvent(event: BlockBreakEvent) = blockBuild(event, event.block, event.player)

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.player.isNotInGameWorld()) return
        if (event.action != Action.PHYSICAL) return
        val material = event.clickedBlock?.type ?: return
        if (material == Material.SOIL) return

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

