/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.inline

import de.astride.planetsystem.api.holder.loadedPlanets
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 25.02.2019 23:07.
 * Current Version: 1.0 (25.02.2019 - 25.02.2019)
 */
inline class UniqueID(val uuid: UUID)

val UniqueID.planet get() = loadedPlanets.find { it.uniqueID == this }