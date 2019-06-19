package de.astride.planetsystem.core.log

import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.log.PlayerKeyLogger
import de.astride.planetsystem.core.PlanetSystem
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BasePlayerKeyLogger(player: Player) : BasePlayerLogger(player), PlayerKeyLogger {

    private val directory = JavaPlugin.getPlugin(PlanetSystem::class.java).dataFolder
    private val configData = ConfigData(directory, "messages.json")
    private val config = @Suppress("DEPRECATION") GsonConfig(configData).load()
    private val messages = SpigotGsonMessages(config).availableMessages

    override fun getValue(key: Any): Any = messages[key.toString()] ?: key

    override fun log(level: Logger.Level, vararg message: Any) =
        super.log(level, *message.map { getValue(it) }.toTypedArray())


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasePlayerKeyLogger) return false

        if (directory != other.directory) return false
        if (configData != other.configData) return false
        if (config != other.config) return false
        if (messages != other.messages) return false

        return true
    }

    override fun hashCode(): Int {
        var result = directory?.hashCode() ?: 0
        result = 31 * result + configData.hashCode()
        result = 31 * result + config.hashCode()
        result = 31 * result + messages.hashCode()
        return result
    }

    override fun toString(): String =
        "BasePlayerKeyLogger(directory=$directory, configData=$configData, config=$config, messages=$messages)"

}
