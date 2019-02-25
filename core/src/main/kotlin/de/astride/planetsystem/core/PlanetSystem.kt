package de.astride.planetsystem.core

import de.astride.planetsystem.core.commands.PlanetCommand
import de.astride.planetsystem.core.commands.modules.ExpandCommand
import de.astride.planetsystem.core.holder.HolderImpl
import de.astride.planetsystem.core.listeners.PlanetCommandListener
import de.astride.planetsystem.core.listeners.PlanetListener
import de.astride.planetsystem.core.listeners.PlayerListener
import de.astride.planetsystem.core.planets.Planet
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

        Planet.planets.addAll(ConfigService.instance.data.planets.load())
        PlanetCommandListener(this)
        ExpandCommand(this, ConfigService.instance.config.permissions?.available ?: emptyMap()) //TODO: IMPL

        logger.info("PlanetSystem started")
    }

    override fun onDisable() = onDisable {
        saveAll()


        HandlerList.unregisterAll(this)
        ConfigService.instance.data.planets.save(Planet.planets)
        Bukkit.getServicesManager().unregisterAll(this)
        Planet.planets.clear()


        logger.info("PlanetSystem stopped")
    }

    private fun saveAll() {
        Holder.instance.planetData.loadedPlanets.forEach { Holder.instance.planetData.save(it.owner) }
        Holder.instance.playerData.players.forEach { Holder.instance.playerData.save(it.owner) }
    }

    private fun register() {
        registerCommands()
        registerListeners()

        //TODO: Add config handling
        Bukkit.getScheduler().runTaskTimer(this, { saveAll() }, 0, 20 * 60)
    }

    private fun registerCommands() {
        PlanetCommand(this)
    }

    private fun registerListeners() {
        PlanetListener(this)
        PlayerListener(this)
    }

}
