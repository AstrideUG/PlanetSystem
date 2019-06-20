/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.api.events.PlayerLeavePlanetEvent
import de.astride.planetsystem.api.functions.outerPlanet
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created on 20.06.2019 21:10.
 * @author Lars Artmann | LartyHD
 */
class PlayerLeavePlanetEventImplementationListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler(priority = EventPriority.LOW)
    fun on(event: PlayerDisconnectEvent) {
        val player = event.player ?: return
        val planet = player.location.outerPlanet ?: return
        PlayerLeavePlanetEvent(player, planet).call()
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun on(event: PlayerMoveEvent) {
        val planet = event.from.outerPlanet ?: return
        if (planet == event.to.outerPlanet) return
        val called = PlayerLeavePlanetEvent(event.player, planet).call()
        if (called.isCancelled) event.cancel()
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun on(event: PlayerTeleportEvent) {
        val player = event.player ?: return
        val planet = event.from.outerPlanet ?: return
        if (planet == event.to.outerPlanet) return
        val called = PlayerLeavePlanetEvent(player, planet).call()
        if (!called.isCancelled || event.cause == PlayerTeleportEvent.TeleportCause.UNKNOWN) return
        player.teleport(event.from, PlayerTeleportEvent.TeleportCause.UNKNOWN)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun on(event: PlayerRespawnEvent) {
        val player = event.player ?: return
        val planet = player.location.outerPlanet ?: return
        if (planet == event.respawnLocation.outerPlanet) return
        val called = PlayerLeavePlanetEvent(player, planet).call()
        if (called.isCancelled) player.teleportPlanetSpawn(planet)
    }

}

