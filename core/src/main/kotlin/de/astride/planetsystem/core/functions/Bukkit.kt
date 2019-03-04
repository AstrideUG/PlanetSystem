package de.astride.planetsystem.core.functions

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:24.
 * Current Version: 1.0 (28.02.2019 - 04.03.2019)
 */
//TODO ADD it to Darkness


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:24.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
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
fun Int.toMaterial(): Material? = @Suppress("DEPRECATION") Material.getMaterial(this)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 10:03.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replace(prefix: String, location: Location) = replace(
    prefix,
    location.x,
    location.y,
    location.z,
    location.yaw,
    location.pitch,
    "World" to location.world.name
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 10:06.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replace(
    prefix: String,
    vector: Vector,
    yaw: Float = 0f,
    pitch: Float = 0f,
    vararg keys: Pair<String, Any?>
) = replace(prefix, vector.x, vector.y, vector.z, yaw, pitch, *keys)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 10:59.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replace(
    prefix: String,
    x: Double,
    y: Double,
    z: Double,
    yaw: Float = 0f,
    pitch: Float = 0f,
    vararg keys: Pair<String, Any?>
) = replaceKeys(
    prefix,
    *mutableListOf(
        *keys,
        "X" to x,
        "Y" to y,
        "Z" to z
    ).apply {
        if (yaw != 0f || pitch != 0f) {
            add("Yaw" to yaw)
            add("Pitch" to pitch)
        }
    }.toTypedArray()
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 11:43.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replacePlayer(prefix: String, uuid: UUID) =
    replaceKeys(prefix, "UUID" to uuid, "Name" to Bukkit.getOfflinePlayer(uuid)?.name)