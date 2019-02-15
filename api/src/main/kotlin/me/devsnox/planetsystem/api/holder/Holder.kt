package me.devsnox.planetsystem.api.holder

import me.devsnox.planetsystem.api.handler.DatabaseHandler
import me.devsnox.planetsystem.api.handler.GridHandler
import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.api.holder.data.PlanetData
import me.devsnox.planetsystem.api.holder.data.PlayerData
import org.bukkit.World
import org.bukkit.entity.Entity

interface Holder {

	val databaseHandler: DatabaseHandler

	val world: World

	val playerData: PlayerData

	val planetData: PlanetData

	val gridHandler: GridHandler

	object Impl {

		lateinit var holder: Holder

	}

}

fun Entity.isNotInHolderWorld() = this.world != holder.world
