/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.listeners.commands

import de.astride.planetsystem.api.events.PlayerEnterPlanetEvent
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.planetPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created on 20.06.2019 23:37.
 * @author Lars Artmann | LartyHD
 */
class PlanetBanCommandListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun on(event: PlayerEnterPlanetEvent) {
        val planet = event.planet
        val owner = Owner(event.player.uniqueId)

        if (owner == planet.owner) return
        if (owner !in planet.banned) return

        event.cancel()
        owner.planetPlayer?.logger?.warn("loadedPlanet.enter.failed.you.are.banned")
    }

}