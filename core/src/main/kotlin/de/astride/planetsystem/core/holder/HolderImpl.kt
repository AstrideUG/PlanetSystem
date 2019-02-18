package de.astride.planetsystem.core.holder

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.data.PlanetData
import de.astride.planetsystem.api.holder.data.PlayerData
import de.astride.planetsystem.core.database.DatabaseHandler
import de.astride.planetsystem.core.holder.data.PlanetDataImpl
import de.astride.planetsystem.core.holder.data.PlayerDataImpl
import de.astride.planetsystem.core.world.GridHandler
import lombok.Data
import org.bukkit.World

@Data
class HolderImpl : Holder {
	override val playerData: PlayerData = PlayerDataImpl(this)
	override val planetData: PlanetData = PlanetDataImpl(this)
	override val databaseHandler: DatabaseHandler = DatabaseHandler()
	override val gridHandler: GridHandler = GridHandler("PlanetWorld", 2048)

	override val world: World get() = gridHandler.world

}

