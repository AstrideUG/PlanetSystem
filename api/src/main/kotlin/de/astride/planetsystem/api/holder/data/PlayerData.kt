package de.astride.planetsystem.api.holder.data

import de.astride.planetsystem.api.player.PlanetPlayer
import java.util.*

interface PlayerData {

	val players: Set<PlanetPlayer>

	fun load(uuid: UUID, request: (PlanetPlayer) -> Unit)

	fun save(uuid: UUID)

	fun unload(uuid: UUID)

	fun getPlayer(uuid: UUID): PlanetPlayer? = players.find { it.uuid == uuid }

}
