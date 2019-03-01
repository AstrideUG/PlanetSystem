package de.astride.planetsystem.core

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.core.commands.PlanetCommand
import de.astride.planetsystem.core.holder.HolderImpl
import de.astride.planetsystem.core.listeners.PlanetCommandListener
import de.astride.planetsystem.core.listeners.PlanetListener
import de.astride.planetsystem.core.listeners.PlayerListener
import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.plugin.DarkPlugin
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.ServicePriority


class PlanetSystem : DarkPlugin() {

    override fun onLoad() = onLoad {
        Bukkit.getServicesManager().register(
            ConfigService::class.java,
            ConfigService(dataFolder),
            this,
            ServicePriority.Normal
        ) //Important for ConfigService.instance
        messages =
            ConfigService.instance.config.spigotGsonMessages.availableMessages //Important for CommandSender.sendConfigurableMessage(name: String)
        Holder.instance = HolderImpl() //For Holder.Impl.holder
    }

    override fun onEnable() = onEnable {
        register()

        PlanetCommandListener(this)

        logger.info("PlanetSystem started")
    }

    override fun onDisable() = onDisable {
        Bukkit.getScheduler().cancelTasks(this)

        saveAll()

        HandlerList.unregisterAll(this)
        Bukkit.getServicesManager().unregisterAll(this)

        logger.info("PlanetSystem stopped")
    }

    private fun register() {
        registerCommands()
        registerListeners()

        //TODO: Add planets handling
        Bukkit.getScheduler().runTaskTimer(this, { if (isEnabled) saveAll() }, 0, 20 * 60)
    }

    private fun registerCommands() {
        PlanetCommand(this)
    }

    private fun registerListeners() {
        PlanetListener(this)
        PlayerListener(this)
    }

    private fun saveAll() {
        Holder.instance.loadedPlanets.forEach { it.save() }
        Holder.instance.players.forEach { it.save() }
    }

}
