package de.astride.planetsystem.api.holder

import de.astride.planetsystem.api.handler.DatabaseHandler
import de.astride.planetsystem.api.handler.GridHandler
import de.astride.planetsystem.api.holder.Holder.Impl.holder
import de.astride.planetsystem.api.holder.data.PlanetData
import de.astride.planetsystem.api.holder.data.PlayerData
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
