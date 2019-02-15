package me.devsnox.planetsystem.core

import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.core.commands.PlanetCommand
import me.devsnox.planetsystem.core.holder.HolderImpl
import me.devsnox.planetsystem.core.listeners.PlanetListener
import me.devsnox.planetsystem.core.listeners.PlayerListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class PlanetSystem : JavaPlugin() {

	override fun onLoad() {
		holder = HolderImpl()
	}

	override fun onEnable() {
		register()
		logger.info("PlanetSystem started")
	}

	override fun onDisable() {
		saveAll()
		logger.info("PlanetSystem stopped")
	}

	private fun saveAll() {
		holder.planetData.loadedPlanets.forEach { holder.planetData.save(it.ownerUniqueID) }
		holder.playerData.players.forEach { holder.playerData.save(it.uuid) }
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
