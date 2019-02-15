package me.devsnox.planetsystem.core.player

import lombok.Data
import lombok.EqualsAndHashCode
import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.location.isInside
import me.devsnox.planetsystem.api.log.KeyLogger
import me.devsnox.planetsystem.api.planet.LoadedPlanet
import me.devsnox.planetsystem.api.planet.Planet
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.log.BasePlayerKeyLogger
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

@EqualsAndHashCode(callSuper = true)
@Data
class BasePlanetPlayer(
		override val player: Player,
		loadedPlanet: LoadedPlanet,
		override val memberedPlanets: List<Planet>
) : BaseOfflinePlanetPlayer(player.uniqueId /*IGNORED*/, loadedPlanet, memberedPlanets), PlanetPlayer {

	override val uuid: UUID get() = super<PlanetPlayer>.uuid

	override val planet: LoadedPlanet get() = super.planet as LoadedPlanet
	override val isOnHisPlanet: Boolean get() = planet.inner.isInside(location)
	override val location: PlanetLocation get() = PlanetLocation(planet, player.location)

	override val logger: KeyLogger = BasePlayerKeyLogger(player)

	override fun canBuild(location: Location): Boolean = if (isOnHisPlanet) true
	else
		holder.planetData.getPlanet(location)?.members?.contains(player.uniqueId) ?: false

}
