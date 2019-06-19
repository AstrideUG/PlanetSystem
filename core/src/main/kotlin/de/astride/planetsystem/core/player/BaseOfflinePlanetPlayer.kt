package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.OfflinePlanetPlayer
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.players
import org.bukkit.Bukkit

open class BaseOfflinePlanetPlayer(
    override val owner: Owner,
    override val planet: Planet
) : OfflinePlanetPlayer {

    override fun load(request: (PlanetPlayer) -> Unit) {

//        val databasePlanet = Holder.instance.databaseHandler.getPlanet(owner)
//        databasePlanet.toPlanet()

        planet.load { loadedPlanet ->
            val planetPlayer = BasePlanetPlayer(Bukkit.getPlayer(owner.uuid), loadedPlanet)

            players += planetPlayer
            request(planetPlayer)
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseOfflinePlanetPlayer) return false

        if (owner != other.owner) return false
        if (planet != other.planet) return false

        return true
    }

    override fun hashCode(): Int {
        var result = owner.hashCode()
        result = 31 * result + planet.hashCode()
        return result
    }

    override fun toString(): String = "BaseOfflinePlanetPlayer(owner=$owner, planet=$planet)"

}
