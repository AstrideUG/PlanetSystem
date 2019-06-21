/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.listeners.commands

import de.astride.planetsystem.api.events.PlayerLeavePlanetEvent
import de.astride.planetsystem.api.planet.isNotEmpty
import de.astride.planetsystem.api.proxies.player
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created on 20.06.2019 23:37.
 * @author Lars Artmann | LartyHD
 */
class PlanetVisitCommandListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun on(event: PlayerLeavePlanetEvent) {
        val planet = event.planet
        if (planet.isNotEmpty) return
        if (planet.owner.player != null) return
        planet.unload()
    }


}