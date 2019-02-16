package me.devsnox.planetsystem.api.player

import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.log.Logger
import me.devsnox.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

interface PlanetPlayer : OfflinePlanetPlayer {

	val player: Player

	override val planet: LoadedPlanet

	val isOnHisPlanet: Boolean

	val location: PlanetLocation

	val logger: Logger

	fun canBuild(location: Location): Boolean

	fun canBuild(block: Block): Boolean = canBuild(block.location)

}
