/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.listeners

import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.general.functions.toNonNull
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.08.2018 14:54.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 24.02.2019
 */
class PlanetCommandListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @Suppress("unused")
    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        fun String.checkTitle() =
            if (length > 31) event.inventory.title == substring(0, 31) else event.inventory.title == this

        val player = (event.whoClicked as? Player).toNonNull("Player")
        val displayName = event.currentItem?.itemMeta?.displayName ?: return
        val block = arrayOf(
            messages["Planet.Command.NoArgs.Inventory.Name"]?.takeIf { it.checkTitle() }?.apply {
                val prefix = "Planet.Command.NoArgs.Inventory.Entry."
                when (displayName) {
                    messages["${prefix}Info"] -> player.command("Info")
                    messages["${prefix}Shape"] -> player.command("Shape")
                    messages["${prefix}Style"] -> player.command("Style")
                    messages["${prefix}Expand"] -> player.command("Expand")
                }
            },
            messages["Planet.Command.Shape.Inventory.Name"]?.takeIf { it.checkTitle() }?.apply {
                val prefix = "Planet.Command.Shape.Inventory.Entry."
                takeIf { displayName == messages["${prefix}Cube"] }?.apply { player.command("Shape Cube") }
            },
            messages["Planet.Command.Style.Inventory.Name"]?.takeIf { it.checkTitle() }?.apply {
                //                val prefix = "Planet.Command.Style.Inventory.Entry."
                takeIf { displayName == ChatColor.GREEN.toString()/*messages["${prefix}Style"]*/ }?.apply {
                    player.command("Style SubID ${event.currentItem.durability}")
                }
            },
            messages["Planet.Command.Expand.Inventory.Name"]?.takeIf { it.checkTitle() }?.apply {
                val prefix = "Planet.Command.Expand.Inventory.Entry."
                takeIf { displayName == messages["${prefix}Expand"] }?.apply {
                    player.command("Expand Force")
                    player.closeInventory()
                }
            }
        ).any { it != null }
        if (block) event.cancel()
    }

}

private fun Player.command(args: String) = this.chat("/${ConfigService.instance.config.planetCommand} Expand $args")



