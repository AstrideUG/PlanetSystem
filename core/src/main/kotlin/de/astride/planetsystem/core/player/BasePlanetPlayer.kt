package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.log.KeyLogger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.log.BasePlayerKeyLogger
import lombok.Data
import lombok.EqualsAndHashCode
import org.bukkit.entity.Player

@EqualsAndHashCode(callSuper = true)
@Data
class BasePlanetPlayer(
    override val player: Player,
    loadedPlanet: LoadedPlanet,
    override val memberedPlanets: List<Planet>
) : BaseOfflinePlanetPlayer(Owner(player.uniqueId), loadedPlanet, memberedPlanets), PlanetPlayer {

    override val logger: KeyLogger = BasePlayerKeyLogger(player)

    override val planet: LoadedPlanet get() = super.planet as LoadedPlanet
    override val owner: Owner get() = super<PlanetPlayer>.owner

}
