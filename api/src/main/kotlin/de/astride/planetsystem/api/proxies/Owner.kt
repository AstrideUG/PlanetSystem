/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.proxies

import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.database.OfflinePlayer
import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.players
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 25.02.2019 23:01.
 * Current Version: 1.0 (25.02.2019 - 25.02.2019)
 */
data class Owner(val uuid: UUID)

val Owner.planetPlayer get() = players.find { it.owner == this }
val Owner.loadedPlanet get() = planetPlayer?.planet
val Owner.player get() = planetPlayer?.player

val Owner.databasePlayer: OfflinePlayer? get() = databaseHandler.findPlayer(this)
val Owner.planet: OfflinePlanet? get() = databasePlayer?.planetUniqueId?.let { databaseHandler.findPlanet(it) }

