package de.astride.planetsystem.core.holder

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.database.DatabaseHandler
import de.astride.planetsystem.core.proxies.config
import de.astride.planetsystem.core.world.BaseGridHandler
import lombok.Data

@Data
class HolderImpl : Holder {

    override val databaseHandler: DatabaseHandler = DatabaseHandler()
    override val gridHandler: BaseGridHandler = BaseGridHandler(config.gameWorld, config.gridMaxSize)
    override val players: MutableSet<PlanetPlayer> = mutableSetOf()
    override val loadedPlanets: MutableSet<LoadedPlanet> = mutableSetOf()

}

