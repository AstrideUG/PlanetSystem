/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.listeners.commands

import de.astride.planetsystem.core.proxies.config
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 14.03.2019 14:46.
 * Current Version: 1.0 (14.03.2019 - 19.06.2019)
 */
class PlanetDeleteCommandListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 14.03.2019 14:47.
     * Current Version: 1.0 (14.03.2019 - 14.03.2019)
     */
    @EventHandler
    fun on(event: InventoryClickEvent) {

        val item = event.currentItem ?: return

        val inventory = config.commands.restart.inventory
        if (inventory != event.inventory) return

        event.cancel()

        if (inventory.getItem(13) != item) return

        event.whoClicked.closeInventory()
        Bukkit.dispatchCommand(event.whoClicked, "${config.planetCommand} delete confirmed")

    }


}