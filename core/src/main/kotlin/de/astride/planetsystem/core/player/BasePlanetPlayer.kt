package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.holder.Holder.Impl.holder
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.log.KeyLogger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.log.BasePlayerKeyLogger
import lombok.Data
import lombok.EqualsAndHashCode
import org.bukkit.Location
import org.bukkit.entity.Player

@EqualsAndHashCode(callSuper = true)
@Data
class BasePlanetPlayer(
		override val player: Player,
		loadedPlanet: LoadedPlanet,
		override val memberedPlanets: List<Planet>
) : BaseOfflinePlanetPlayer(player.uniqueId, loadedPlanet, memberedPlanets), PlanetPlayer {

	override val planet: LoadedPlanet get() = super.planet as LoadedPlanet
	override val isOnHisPlanet: Boolean get() = planet.inner.isInside(location)
	override val location: PlanetLocation get() = PlanetLocation(planet, player.location)

	override val logger: KeyLogger = BasePlayerKeyLogger(player)

	override fun canBuild(location: Location): Boolean = if (isOnHisPlanet) true
	else
		holder.planetData.getPlanet(location)?.members?.contains(player.uniqueId) ?: false

}
