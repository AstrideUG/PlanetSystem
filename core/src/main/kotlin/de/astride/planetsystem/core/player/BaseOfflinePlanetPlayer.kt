package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.OfflinePlanetPlayer
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.database.DatabasePlayer
import lombok.Data
import org.bukkit.Bukkit

@Data
open class BaseOfflinePlanetPlayer(
    override val owner: Owner,
    override val planet: Planet,
    override val memberedPlanets: Set<Planet>
) : OfflinePlanetPlayer {

    override fun load(request: (PlanetPlayer) -> Unit) {

//        val databasePlanet = Holder.instance.databaseHandler.getPlanet(owner)
//        databasePlanet.toPlanet()

        planet.load { loadedPlanet ->
            val members =
                setOf<Planet>()//TODO databasePlayer.getMemberedPlanets().stream().map(this::find).collect(Collectors.toList());
            val planetPlayer = BasePlanetPlayer(Bukkit.getPlayer(owner.uuid), loadedPlanet, members)

            Holder.instance.players += planetPlayer
            request(planetPlayer)
        }

    }

}

//TODO: delete?
fun BaseOfflinePlanetPlayer.toDatabasePlayer(): DatabasePlayer =
    DatabasePlayer(owner.uuid, planet.uniqueID.uuid, memberedPlanets.map { it.uniqueID.uuid })
