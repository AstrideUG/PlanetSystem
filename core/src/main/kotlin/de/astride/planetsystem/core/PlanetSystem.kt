package de.astride.planetsystem.core

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.saveAll
import de.astride.planetsystem.core.commands.PlanetCommand
import de.astride.planetsystem.core.holder.HolderImpl
import de.astride.planetsystem.core.listeners.PlanetCommandListener
import de.astride.planetsystem.core.listeners.PlanetListener
import de.astride.planetsystem.core.listeners.PlayerListener
import de.astride.planetsystem.core.listeners.RestartCommandListener
import de.astride.planetsystem.core.proxies.configs
import de.astride.planetsystem.core.service.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.plugin.DarkPlugin
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.ServicePriority
import org.slf4j.LoggerFactory

class PlanetSystem : DarkPlugin() {

    override fun onLoad() = onLoad {
        Bukkit.getServicesManager().register(
            ConfigService::class.java,
            ConfigService(dataFolder),
            this,
            ServicePriority.Normal
        ) //Important for ConfigService.instance
        messages =
            configs.config.spigotGsonMessages.availableMessages //Important for CommandSender.sendConfigurableMessage(name: String)

    }

    override fun onEnable() = onEnable {
        Holder.instance = HolderImpl() //For Holder.Impl.holder

        //For Mongodb logs (stops this stuff)!
        (LoggerFactory.getILoggerFactory() as LoggerContext).getLogger("org.mongodb.driver").level = Level.ERROR

        register()

        logger.info("PlanetSystem started")
    }

    override fun onDisable() = onDisable {
        Bukkit.getScheduler().cancelTasks(this)

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
        PlanetCommandListener(this)
        RestartCommandListener(this)
    }

}
