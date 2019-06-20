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
import org.bukkit.Material.*
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

        val blockedTypes = arrayOf(
            MONSTER_EGG,
            ITEM_FRAME,
            ARMOR_STAND,
            PAINTING,
            CHEST,
            TRAPPED_CHEST,
            ANVIL,
            CAKE_BLOCK,
            DISPENSER,
            DROPPER,
            HOPPER,
            STONE_BUTTON,
            STONE_PLATE,
            TRAP_DOOR,
            LEVER,
            JUKEBOX,
            FURNACE,
            BURNING_FURNACE,
            ACACIA_DOOR,
            BIRCH_DOOR,
            DARK_OAK_DOOR,
            JUNGLE_DOOR,
            SPRUCE_DOOR,
            WOODEN_DOOR,
            ACACIA_FENCE_GATE,
            BIRCH_FENCE_GATE,
            DARK_OAK_FENCE_GATE,
            JUNGLE_FENCE_GATE,
            SPRUCE_FENCE_GATE,
            FENCE_GATE,
            GOLD_PLATE,
            IRON_PLATE,
            REDSTONE_COMPARATOR_OFF,
            REDSTONE_COMPARATOR_ON,
            DIODE_BLOCK_OFF,
            DIODE_BLOCK_ON,
            DAYLIGHT_DETECTOR,
            DAYLIGHT_DETECTOR_INVERTED
        )
        val block = event.clickedBlock ?: return
        if (block.type !in blockedTypes) return
        event.block(block.location, event.player.uniqueId)
    }

    private fun Cancellable.block(location: Location, uuid: UUID) {
        val planetPlayer = Owner(uuid).planetPlayer ?: return
        if (planetPlayer.canEdit(location)) return
        cancel()
    }


}