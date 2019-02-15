package me.devsnox.planetsystem.core.holder.data

import me.devsnox.planetsystem.api.holder.Holder
import me.devsnox.planetsystem.api.holder.data.PlayerData
import me.devsnox.planetsystem.api.planet.Planet
import me.devsnox.planetsystem.api.player.PlanetPlayer
import me.devsnox.planetsystem.core.database.DatabasePlayer
import me.devsnox.planetsystem.core.player.BasePlanetPlayer
import org.bukkit.Bukkit
import java.util.*

data class PlayerDataImpl(val holder: Holder) : PlayerData {

	override val players = HashSet<PlanetPlayer>()

	override fun load(uuid: UUID, request: (PlanetPlayer) -> Unit) {
		//        final me.devsnox.planetsystem.api.database.DatabasePlayer databasePlayer = holder.getDatabaseHandler().getPlayer(uuid);

		holder.planetData.load(uuid) { loadedPlanet ->
			val members = ArrayList<Planet>()//TODO databasePlayer.getMemberedPlanets().stream().map(this::getPlanet).collect(Collectors.toList());
			val planetPlayer = BasePlanetPlayer(Bukkit.getPlayer(uuid), loadedPlanet, members)

			players.add(planetPlayer)
			request(planetPlayer)
		}
	}

	override fun save(uuid: UUID) {
		val player = holder.playerData.getPlayer(uuid) ?: return
		val databasePlayer = DatabasePlayer.by(player.planet)
		holder.databaseHandler.savePlayer(databasePlayer)
	}

	override fun unload(uuid: UUID) {
		save(uuid)
		players.remove(getPlayer(uuid))
	}

}
