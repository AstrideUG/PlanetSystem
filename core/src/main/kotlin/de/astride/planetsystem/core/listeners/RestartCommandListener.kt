package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.core.commands.modules.RestartCommand
import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 14.03.2019 14:46.
 * Current Version: 1.0 (14.03.2019 - 14.03.2019)
 */
class RestartCommandListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 14.03.2019 14:47.
     * Current Version: 1.0 (14.03.2019 - 14.03.2019)
     */
    @EventHandler
    fun on(event: InventoryClickEvent) {

        val item = event.currentItem ?: return

        if (RestartCommand.inventory != event.inventory) return
        if (RestartCommand.inventory.getItem(13) != item) return

        event.whoClicked.closeInventory()
        Bukkit.dispatchCommand(event.whoClicked, "${ConfigService.instance.config.planetCommand} restart confirmed")

    }


}