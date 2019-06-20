/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.inline

import de.astride.planetsystem.api.holder.players
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 25.02.2019 23:01.
 * Current Version: 1.0 (25.02.2019 - 25.02.2019)
 */
inline class Owner(val uuid: UUID)

val Owner.planetPlayer get() = players.find { it.planet.owner == this }
val Owner.planet get() = planetPlayer?.planet
val Owner.player get() = planetPlayer?.player
