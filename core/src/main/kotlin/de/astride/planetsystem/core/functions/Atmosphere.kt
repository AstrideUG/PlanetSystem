package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.atmosphere.Atmosphere

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 05:59.
 * Current Version: 1.0 (28.02.2019 - 04.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 11:41.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replace(prefix: String = "Atmosphere", atmosphere: Atmosphere) = replaceKeys(
    prefix,
    "Size" to atmosphere.size,
    "MaxSize" to atmosphere.maxSize,
    "BlockID" to atmosphere.blockID,
    "BlockDamage" to atmosphere.blockDamage,
    "Material" to atmosphere.blockID.toMaterial(),
    "Price" to atmosphere.price
)
