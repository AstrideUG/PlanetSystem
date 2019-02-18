package de.astride.planetsystem.core.log

import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.log.PlayerKeyLogger
import de.astride.planetsystem.core.PlanetSystem
import lombok.Data
import lombok.EqualsAndHashCode
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@EqualsAndHashCode(callSuper = true)
@Data
class BasePlayerKeyLogger(player: Player) : BasePlayerLogger(player), PlayerKeyLogger {

	private val directory = JavaPlugin.getPlugin(PlanetSystem::class.java).dataFolder
	private val configData = ConfigData(directory, "messages.json")
	private val config = @Suppress("DEPRECATION") GsonConfig(configData).load()
	private val messages = SpigotGsonMessages(config).availableMessages

	override fun getValue(key: Any): Any = messages[key.toString()] ?: key

	override fun log(level: Logger.Level, vararg message: Any) = super.log(level, *message.map { getValue(it) }.toTypedArray())

}
