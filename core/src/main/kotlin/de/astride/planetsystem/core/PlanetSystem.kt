/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import de.astride.planetsystem.api.functions.saveAll
import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.gridHandler
import de.astride.planetsystem.core.commands.PlanetCommand
import de.astride.planetsystem.core.database.DatabaseHandler
import de.astride.planetsystem.core.functions.deleteGameWorld
import de.astride.planetsystem.core.listeners.PlayerEnterPlanetEventImplementationListener
import de.astride.planetsystem.core.listeners.PlayerLeavePlanetEventImplementationListener
import de.astride.planetsystem.core.listeners.PlayerListener
import de.astride.planetsystem.core.listeners.ProtectionListener
import de.astride.planetsystem.core.listeners.commands.PlanetAtmosphereCommandListener
import de.astride.planetsystem.core.listeners.commands.PlanetDeleteCommandListener
import de.astride.planetsystem.core.listeners.commands.PlanetVisitCommandListener
import de.astride.planetsystem.core.proxies.configs
import de.astride.planetsystem.core.service.ConfigService
import de.astride.planetsystem.core.world.BaseGridHandler
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
        databaseHandler = DatabaseHandler()
        gridHandler = BaseGridHandler(configs.config.gameWorld, configs.config.gridMaxSize)

//        Flags.FireTick.world = Holder.instance.gridHandler.world

        //For Mongodb logs (stops this stuff)!
        (LoggerFactory.getILoggerFactory() as LoggerContext).getLogger("org.mongodb.driver").level = Level.ERROR

        register()

        logger.info("PlanetSystem started")
    }

    override fun onDisable() = onDisable {
        Bukkit.getScheduler().cancelTasks(this)

        HandlerList.unregisterAll(this)
        Bukkit.getServicesManager().unregisterAll(this)

        deleteGameWorld()

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
        PlanetAtmosphereCommandListener(this)
        PlanetDeleteCommandListener(this)
        PlanetVisitCommandListener(this)

        PlayerEnterPlanetEventImplementationListener(this)
        PlayerLeavePlanetEventImplementationListener(this)

        PlayerListener(this)
        ProtectionListener(this)
    }

}
