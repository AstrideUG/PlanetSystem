package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.holder.isNotInGameWorld
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.databaseHandler
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.api.proxies.players
import de.astride.planetsystem.core.flags.Flags
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
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (15.02.2019 - 18.03.2019)
 */
class PlayerListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun on(event: PlayerChangedWorldEvent) {
        if (event.player.isNotInGameWorld()) return
        event.player.gameMode = GameMode.SURVIVAL
    }

//    @EventHandler
//    fun on(event: PlayerMoveEvent) {
//        if (event.player.isNotInGameWorld()) return
//        val planet = loadedPlanets.find { it.outer.isInside(PlanetLocation(it, event.to)) } ?: return
//        if (!planet.inner.isInside(event.to.toVector())) event.player.teleportHome(planet)
//    }

    @EventHandler
    fun onPlayerLoginEvent(event: PlayerJoinEvent) {
        val owner = Owner(event.player.uniqueId)
        val databasePlanet = databaseHandler.getDatabasePlanet(UniqueID(UUID.randomUUID()), owner)
        BaseOfflinePlanetPlayer(owner, databasePlanet.toPlanet()).load { it.teleportHome() }
    }

    @EventHandler
    fun onPlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        val owner = Owner(event.player.uniqueId)
        loadedPlanets.find(owner)?.unload()
        players.find(owner)?.unload()
    }

    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {
        val planet = loadedPlanets.find(Owner(event.player.uniqueId)) ?: return
        event.respawnLocation = planet.spawnLocation.toBukkitLocation(planet)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        if (Flags.PvP.value) return

        val damager = event.damager
        val type = damager.type
        when {
            event.entityType != PLAYER -> return
            event.entity.isNotInGameWorld() -> return
            type == PLAYER ||
                    type == PRIMED_TNT && (damager as TNTPrimed).source.type == PLAYER ||
                    type == ARROW && (damager as Arrow).shooter is Player ||
                    type == FISHING_HOOK && (damager as FishHook).shooter is Player -> event.cancel()
        }

    }

}

fun Player.teleportHome(planet: LoadedPlanet) = teleport(planet.spawnLocation.toBukkitLocation(planet))
fun PlanetPlayer.teleportHome() = player.teleportHome(planet)
