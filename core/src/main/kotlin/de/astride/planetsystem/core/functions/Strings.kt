package de.astride.planetsystem.core.functions

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:13.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:13.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
fun String?.replace(key: String, value: Any?) = this?.replace(key, value.toString(), true)