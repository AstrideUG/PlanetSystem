package me.devsnox.planetsystem.core.log

import com.google.gson.JsonObject
import lombok.Data
import lombok.EqualsAndHashCode
import me.devsnox.planetsystem.api.log.Logger
import me.devsnox.planetsystem.api.log.PlayerKeyLogger
import me.devsnox.planetsystem.core.PlanetSystem
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@EqualsAndHashCode(callSuper = true)
@Data
class BasePlayerKeyLogger(player: Player, mapper: MutableMap<Any, Any>) : BasePlayerLogger(player), PlayerKeyLogger {

	private val mapper: Map<Any, Any>

	init {
		this.mapper = mapper
		if (messages == null)
			messages = SpigotGsonMessages(GsonConfig(ConfigData(JavaPlugin.getPlugin(PlanetSystem::class.java).dataFolder, "messages.json", true), JsonObject(), true).load()).availableMessages
		mapper.putAll(messages!!)
	}

	override fun getValue(key: Any): Any {
		val o = this.mapper[key]
		return o ?: key
	}

	override fun log(level: Logger.Level, vararg message: Any) {
		val objects = ArrayList<Any>()

		for (o in message) objects.add(this.getValue(o))

		super.log(level, *objects.toTypedArray())
	}

	companion object {
		private var messages: Map<String, String>? = null
	}

}
