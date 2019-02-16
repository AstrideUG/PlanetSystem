package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

interface PlanetPlayer : OfflinePlanetPlayer {

	override val uuid: UUID get() = player.uniqueId

	val player: Player

	override val planet: LoadedPlanet

	val isOnHisPlanet: Boolean

	val location: PlanetLocation

	val logger: Logger

	fun canBuild(location: Location): Boolean

	fun canBuild(block: Block): Boolean = canBuild(block.location)

}
