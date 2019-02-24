package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.holder.Holder.Impl.holder
import de.astride.planetsystem.api.holder.isNotInHolderWorld
import de.astride.planetsystem.api.location.toBukkitLocation
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType.*
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlayerListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val uniqueId = event.player.uniqueId
        holder.databaseHandler.create(UUID.randomUUID(), uniqueId) { _ ->
            holder.playerData.load(uniqueId) {
                it.player.teleport(it.planet.spawnLocation.toBukkitLocation())
            }
        }
    }

    @EventHandler
    fun onPlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        val owner = event.player.uniqueId
        holder.planetData.unload(owner)
        holder.playerData.unload(owner)
    }

    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {
        event.respawnLocation = holder
            .planetData
            .getLoadedPlanetByOwner(event.player.uniqueId)
            ?.spawnLocation
            ?.toBukkitLocation()
            ?: return
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        val type = damager.type
        when {
            event.entity.type != PLAYER -> return
            event.entity.isNotInHolderWorld() -> return
            type == PLAYER ||
                    type == PRIMED_TNT && (damager as TNTPrimed).source.type == PLAYER ||
                    type == ARROW && (damager as Arrow).shooter is Player ||
                    type == FISHING_HOOK && (damager as FishHook).shooter is Player -> event.cancel()
        }

    }

}
