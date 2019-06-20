/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.planetPlayer
import de.astride.planetsystem.api.player.canBuild
import de.astride.planetsystem.core.flags.Flags
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 08.03.2019 02:28.
 * Current Version: 1.0 (08.03.2019 - 18.03.2019)
 */
class ProtectionListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun on(event: EntityDamageByEntityEvent) {
        if (event.damager?.type != EntityType.PLAYER) return
        if (Flags.Mobs.types.any { it == event.entityType }) return
        event.cancel()
    }

    @EventHandler
    fun on(event: PlayerBucketEmptyEvent) = event.block(event.blockClicked.location, event.player.uniqueId)

    @EventHandler
    fun on(event: PlayerBucketFillEvent) = event.block(event.blockClicked.location, event.player.uniqueId)

    @EventHandler
    fun on(event: PlayerInteractEntityEvent) = event.block(event.rightClicked.location, event.player.uniqueId)

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
            type != Material.LEVER
        ) return

        event.block(block.location, event.player.uniqueId)

    }

    private fun Cancellable.block(location: Location, uuid: UUID) {
        val planetPlayer = Owner(uuid).planetPlayer ?: return
        if (planetPlayer.canBuild(location)) return
        cancel()
    }


}