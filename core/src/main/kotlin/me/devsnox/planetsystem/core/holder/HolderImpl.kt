package me.devsnox.planetsystem.core.holder

import lombok.Data
import me.devsnox.planetsystem.api.holder.Holder
import me.devsnox.planetsystem.api.holder.data.PlanetData
import me.devsnox.planetsystem.api.holder.data.PlayerData
import me.devsnox.planetsystem.core.database.DatabaseHandler
import me.devsnox.planetsystem.core.holder.data.PlanetDataImpl
import me.devsnox.planetsystem.core.holder.data.PlayerDataImpl
import me.devsnox.planetsystem.core.world.GridHandler
import org.bukkit.World

@Data
class HolderImpl : Holder {
	override val playerData: PlayerData = PlayerDataImpl(this)
	override val planetData: PlanetData = PlanetDataImpl(this)
	override val databaseHandler: DatabaseHandler = DatabaseHandler()
	override val gridHandler: GridHandler = GridHandler("PlanetWorld", 2048)

	override val world: World get() = gridHandler.world

}

