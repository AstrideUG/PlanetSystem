package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.OfflinePlanetPlayer
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.players
import lombok.Data
import org.bukkit.Bukkit

@Data
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

}
