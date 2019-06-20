/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.functions.innerPlanet
import de.astride.planetsystem.api.functions.outerPlanet
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.planetPlayer
import de.astride.planetsystem.api.player.canEdit
import de.astride.planetsystem.core.flags.Flags
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.*
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * Created on 08.03.2019 02:28.
 * @author Lars Artmann | LartyHD
 */
class ProtectionListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    /**
     * Blocks lava and water flows out of inner region and from outer region
     * @author Lars Artmann | LartyHD
     */
    @EventHandler
    fun on(event: BlockFromToEvent) {
        val from = event.block.location ?: return
        val to = event.toBlock.location ?: return
        if (from.innerPlanet != null) to.innerPlanet ?: event.cancel()
        else if (from.outerPlanet != null) event.cancel()
    }

    @EventHandler
    fun on(event: EntityDamageByEntityEvent) {
        if (event.damager?.type != EntityType.PLAYER) return
        if (Flags.Mobs.types.any { it == event.entityType }) return
        event.cancel()
    }

    @EventHandler
    fun on(event: PlayerBucketEmptyEvent) =
        event.block(event.blockClicked.getRelative(event.blockFace).location, event.player.uniqueId)

    @EventHandler(priority = EventPriority.HIGHEST)
    fun on(event: PlayerBucketFillEvent) {
        val player = event.player
        val block = event.blockClicked.getRelative(event.blockFace)
        val location = block.location ?: return
        event.block(location, player.uniqueId)
        if (event.isCancelled) {
            player.updateInventory()
            @Suppress("DEPRECATION")
            player.sendBlockChange(location, block.type, block.data)
        }
    }

    @EventHandler
    fun on(event: PlayerInteractEntityEvent) = event.block(event.rightClicked.location, event.player.uniqueId)

    @EventHandler
    fun on(event: PlayerArmorStandManipulateEvent) = event.block(event.rightClicked.location, event.player.uniqueId)

    /**
     * Blocks piston extension in outer region
     * @author Lars Artmann | LartyHD
     */
    @EventHandler
    fun on(event: BlockPistonExtendEvent) {
        val blocks: List<Block> =
            (event.blocks.ifEmpty { listOf(event.block) }).map { it.getRelative(event.direction) }
        if (blocks.any { it.location.outerPlanet == null || it.location.innerPlanet == null }) event.cancel()
    }

    /**
     * Blocks piston extension in outer region
     * @author Lars Artmann | LartyHD
     */
    @EventHandler
    fun on(event: BlockPistonRetractEvent) {
        val blocks: List<Block> = event.blocks.ifEmpty { listOf(event.block.getRelative(event.direction)) }
        if (blocks.any { it.location.outerPlanet == null || it.location.innerPlanet == null }) event.cancel()
    }

    @EventHandler
    fun on(event: PlayerInteractEvent) {

        val block = event.clickedBlock ?: return
        val type = block.type
        if (type != Material.CHEST &&
            type != Material.TRAPPED_CHEST &&
            type != Material.ANVIL &&
            type != Material.CAKE_BLOCK &&
            type != Material.DISPENSER &&
            type != Material.DROPPER &&
            type != Material.HOPPER &&
            type != Material.STONE_BUTTON &&
            type != Material.STONE_PLATE &&
            type != Material.TRAP_DOOR &&
            type != Material.LEVER &&
            type != Material.JUKEBOX &&
            type != Material.FURNACE &&
            type != Material.BURNING_FURNACE &&
            type != Material.ACACIA_DOOR &&
            type != Material.BIRCH_DOOR &&
            type != Material.DARK_OAK_DOOR &&
            type != Material.JUNGLE_DOOR &&
            type != Material.SPRUCE_DOOR &&
            type != Material.WOODEN_DOOR &&
            type != Material.ACACIA_FENCE_GATE &&
            type != Material.BIRCH_FENCE_GATE &&
            type != Material.DARK_OAK_FENCE_GATE &&
            type != Material.JUNGLE_FENCE_GATE &&
            type != Material.SPRUCE_FENCE_GATE &&
            type != Material.FENCE_GATE &&
            type != Material.GOLD_PLATE &&
            type != Material.IRON_PLATE &&
            type != Material.REDSTONE_COMPARATOR_OFF &&
            type != Material.REDSTONE_COMPARATOR_ON &&
            type != Material.DIODE_BLOCK_OFF &&
            type != Material.DIODE_BLOCK_ON &&
            type != Material.DAYLIGHT_DETECTOR &&
            type != Material.DAYLIGHT_DETECTOR_INVERTED &&
            type != Material.LEVER
        ) return

        event.block(block.location, event.player.uniqueId)

    }

    private fun Cancellable.block(location: Location, uuid: UUID) {
        val planetPlayer = Owner(uuid).planetPlayer ?: return
        if (planetPlayer.canEdit(location)) return
        cancel()
    }


}