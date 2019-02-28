package de.astride.planetsystem.core.functions

import org.bukkit.Material

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:24.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
//TODO ADD it to Darkness


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:24.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
//TODO ADD it to Darkness
fun String.toMaterial(): Material? = try {
    toInt().toMaterial()
} catch (ex: NumberFormatException) {
    Material.getMaterial(this)
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:37.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
//TODO ADD it to Darkness
fun Int.toMaterial(): Material? = @Suppress("DEPRECATION") Material.getMaterial(this)