package de.astride.planetsystem.core.functions

import org.bukkit.ChatColor

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
//TODO ADD it to Darkness
fun String?.replace(key: String, value: Any?) = this?.replace(key, value.toString(), true)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 10:53.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replaceKeys(prefix: String, vararg keys: Pair<String, Any?>): String? {
    @Suppress("NAME_SHADOWING")
    val prefix = if (prefix.isEmpty()) "" else "$prefix."
    var result = replace(
        "<${prefix}Lined>",
        keys.toList().joinToString("\n  ") { (key, value) ->
            "$key: ${ChatColor.WHITE}$value"
        }
    )
    keys.forEach { (key, value) -> result = result.replace("<$prefix$key>", "${ChatColor.WHITE}$value") }
    return result
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 11:29.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replaceKeys(prefix: String, vararg values: List<Any>): String? = this
    .replace("<$prefix>", values)
    .replace("<$prefix.Lined>", values.joinToString("") { "\n  - ${ChatColor.WHITE}$it" })

