package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.holder.isNotInHolderWorld
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.functions.toPlanet
import de.astride.planetsystem.core.player.BaseOfflinePlanetPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.GameMode
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType.*
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PlayerListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    private val holder get() = Holder.instance

    @EventHandler
    fun on(event: PlayerChangedWorldEvent) {
        if (event.player.isNotInHolderWorld()) return
        event.player.gameMode = GameMode.SURVIVAL
    }

    @EventHandler
    fun on(event: PlayerMoveEvent) {
        if (event.player.isNotInHolderWorld()) return
        val planet = holder.loadedPlanets.find(event.player.location) ?: return
        val vector = event.to.toVector()
        if (!planet.inner.isInside(vector) && planet.outer.isInside(vector))
            event.player.teleportHome(planet)
    }

    @EventHandler
    fun onPlayerLoginEvent(event: PlayerJoinEvent) {
        val owner = Owner(event.player.uniqueId)
        val databasePlanet = holder.databaseHandler.getDatabasePlanet(UniqueID(UUID.randomUUID()), owner)
        BaseOfflinePlanetPlayer(owner, databasePlanet.toPlanet()).load { it.teleportHome() }
    }

    @EventHandler
    fun onPlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        val owner = Owner(event.player.uniqueId)
        println(owner)
        println(holder.loadedPlanets)
        println(holder.players)
        holder.loadedPlanets.find(owner)?.unload()
        holder.players.find(owner)?.unload()
    }

    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {
        event.respawnLocation =
            holder.loadedPlanets.find(Owner(event.player.uniqueId))?.spawnLocation?.toBukkitLocation() ?: return
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

    private fun Player.teleportHome(planet: Planet) = teleport(planet.spawnLocation.toBukkitLocation())
    private fun PlanetPlayer.teleportHome() = player.teleportHome(planet)

}
