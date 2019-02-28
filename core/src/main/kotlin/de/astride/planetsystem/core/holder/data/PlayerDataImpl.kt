package de.astride.planetsystem.core.holder.data

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.data.PlayerData
import de.astride.planetsystem.api.holder.data.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.database.DatabasePlayer
import de.astride.planetsystem.core.player.BasePlanetPlayer
import org.bukkit.Bukkit
import java.util.*

data class PlayerDataImpl(val holder: Holder) : PlayerData {

    override val players = HashSet<PlanetPlayer>()

    override fun load(owner: Owner, request: (PlanetPlayer) -> Unit) {

        holder.planetData.load(owner) {
            val members =
                setOf<Planet>()//TODO databasePlayer.getMemberedPlanets().stream().map(this::find).collect(Collectors.toList());
            val planetPlayer = BasePlanetPlayer(Bukkit.getPlayer(owner.uuid), it, members)

            players.add(planetPlayer)
            request(planetPlayer)
        }

    }

    override fun save(owner: Owner) {
        val player = holder.playerData.find(owner) ?: return
        val databasePlayer = DatabasePlayer.by(player.planet)
        holder.databaseHandler.savePlayer(databasePlayer)
    }

    override fun unload(owner: Owner) {
        save(owner)
        players.remove(find(owner))
    }

}
