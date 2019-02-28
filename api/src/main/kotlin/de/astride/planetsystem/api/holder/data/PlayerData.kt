package de.astride.planetsystem.api.holder.data

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.PlanetPlayer

interface PlayerData {

    val players: Set<PlanetPlayer>

    fun load(owner: Owner, request: (PlanetPlayer) -> Unit)

    fun save(owner: Owner)

    fun unload(owner: Owner)

}

fun PlayerData.find(owner: Owner): PlanetPlayer? = players.find { it.owner == owner }
