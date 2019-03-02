package de.astride.planetsystem.core.functions

import com.sk89q.worldedit.function.pattern.BlockPattern
import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.core.service.ConfigService
import de.astride.planetsystem.core.utils.FaweUtils
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 00:43.
 * Current Version: 1.0 (26.02.2019 - 28.02.2019)
 */

///**
// * @author Lars Artmann | LartyHD
// * Created by Lars Artmann | LartyHD on 26.02.2019 00:43.
// * Current Version: 1.0 (26.02.2019 - 26.02.2019)
// */
//fun DatabasePlanet.toPlanet(): Planet = BasePlanet(
//    UniqueID(uuid),
//    name,
//    Owner(ownerUniqueId),
//    members.map(::Owner).toMutableList(),
//    size,
//    planetLocation
//)


fun main() {
    val a = java.lang.Boolean(java.lang.Boolean.valueOf(java.lang.Boolean.FALSE.toString())).booleanValue()
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 19:11.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
@Suppress("DEPRECATION")
//TODO Rename
fun LoadedPlanet.place(
    location: Location = middle,
    size: Double = atmosphere.size.toDouble(),
    blockPattern: BlockPattern = BlockPattern(atmosphere.blockID, atmosphere.blockDamage)
): Unit = FaweUtils.setHSphere(location, size, blockPattern)


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 19:18.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
@Suppress("DEPRECATION")
fun LoadedPlanet.delete(
    location: Location = middle,
    size: Double = atmosphere.size.toDouble()
): Unit = FaweUtils.setHSphere(location, size, BlockPattern(0))


///**
// * @author Lars Artmann | LartyHD
// * Created by Lars Artmann | LartyHD on 28.02.2019 06:44.
// * Current Version: 1.0 (28.02.2019 - 28.02.2019)
// */
////TODO make logging better (AOP?)!
//fun LoadedPlanet?.orMessage(
//    sender: CommandSender,
//    byTarget: Boolean = false
//): LoadedPlanet? {
//    val prefix = if (byTarget) "Target" else "Player"
//    if (this == null) sender.sendMessage(
//        messages["${prefix}AreNotAOwnerOfAIsland"]
//            ?.replace("<Sender>", sender.name, true)
//    )
//    return this
//}


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 06:43.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
//TODO make logging better (AOP?)!
fun Holder.findOrMessage(
    owner: Owner,
    sender: CommandSender,
    byTarget: Boolean = false
): LoadedPlanet? {
    val prefix = if (byTarget) "Target" else "Player"
    val planet = find(owner)
    if (planet == null) sender.sendMessage(
        messages["${prefix}AreNotAOwnerOfAIsland"]
            ?.replace("<UUID>", owner.toString(), true)
            ?.replace("<Target>", Bukkit.getOfflinePlayer(owner.uuid).name, true)
            ?.replace("<Sender>", sender.name, true)
    )
    return planet
}


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:44.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
val Atmosphere.price get() = generatePrice(size.toDouble())


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:45.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
fun generatePrice(size: Double) = Math.pow(size, ConfigService.instance.config.planets.pow)


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:51.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
fun String?.replacePrice(planet: Planet) = replace("<Price>", planet.atmosphere.price)
    .replace("<NextPrice>", generatePrice(planet.atmosphere.size + 1.toDouble()))


///**
// * @author Lars Artmann | LartyHD
// * Created by Lars Artmann | LartyHD on 28.02.2019 09:15.
// * Current Version: 1.0 (28.02.2019 - 28.02.2019)
// */
//private inline fun CommandSender.hasPermissionBy(permissionKey: String, block: () -> Unit) =
//    if (hasPermission(this, permissions[permissionKey] ?: "")) block() else sendMessage(permissionMessage)