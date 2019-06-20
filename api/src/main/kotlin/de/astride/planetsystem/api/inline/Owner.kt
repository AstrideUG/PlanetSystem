/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.inline

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.players
import de.astride.planetsystem.api.player.owner
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 25.02.2019 23:01.
 * Current Version: 1.0 (25.02.2019 - 25.02.2019)
 */
inline class Owner(val uuid: UUID)

val Owner.planetPlayer get() = players.find { it.owner == this }
val Owner.planet get() = planetPlayer?.planet
val Owner.player get() = planetPlayer?.player

val Owner.databasePlayer: DatabasePlayer? get() = databaseHandler.findPlayer(this)
val Owner.databasePlanet: DatabasePlanet? get() = databasePlayer?.planetUniqueId?.let { databaseHandler.findPlanet(it) }

