package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.atmosphere.MutableAtmosphere
import de.astride.planetsystem.core.atmosphere.CheckedAtmosphere

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 05:59.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 06:00.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
fun Atmosphere.toMutable(): MutableAtmosphere = CheckedAtmosphere(this)