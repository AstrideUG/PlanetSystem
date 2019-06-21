/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.proxies

import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.loadedPlanets
import java.util.*

/**
 * Created on 25.02.2019 23:07.
 * @author Lars Artmann | LartyHD
 */
interface UniqueID {
    val uuid: UUID
}

val UniqueID.planet get() = loadedPlanets.find { it.uniqueID == this }
val UniqueID.databasePlanet get() = databaseHandler.findPlanet(this)