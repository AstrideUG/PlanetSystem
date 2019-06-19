@file:Suppress("DEPRECATION")

package de.astride.planetsystem.core.functions

import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.patterns.Pattern
import com.sk89q.worldedit.patterns.SingleBlockPattern
import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.api.proxies.players
import de.astride.planetsystem.core.planet.BasePlanet
import de.astride.planetsystem.core.player.BasePlanetPlayer
import de.astride.planetsystem.core.proxies.config
import de.astride.planetsystem.core.utils.FaweUtils
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayer
import org.bukkit.Location
import org.bukkit.command.CommandSender

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 00:43.
 * Current Version: 1.0 (26.02.2019 - 03.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 03.03.2019 21:32.
 * Current Version: 1.0 (03.03.2019 - 03.03.2019)
 */
fun DatabasePlanet.toPlanet(): Planet = BasePlanet(
    uuid,
    name,
    ownerUniqueId,
    members.toMutableSet(),
    planetLocation,
    atmosphere,
    metaData
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 19:11.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
//TODO Rename
fun LoadedPlanet.place(
    location: Location = middle,
    size: Double = atmosphere.size.toDouble(),
    pattern: Pattern = SingleBlockPattern(BaseBlock(atmosphere.blockID, atmosphere.blockDamage))
): Unit = FaweUtils.setHSphere(location, size, pattern)


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 19:18.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
fun LoadedPlanet.delete(
    location: Location = middle,
    size: Double = atmosphere.size.toDouble()
): Unit = FaweUtils.setHSphere(location, size, SingleBlockPattern(BaseBlock(0)))


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 06:43.
 * Current Version: 1.0 (28.02.2019 - 18.03.2019)
 */
//TODO make logging better (AOP?)!
fun findPlanetOrMessage(
    owner: Owner,
    sender: CommandSender,
    byTarget: Boolean = false
): LoadedPlanet? {
    val prefix = if (byTarget) "Target" else "Player"
    val planet = loadedPlanets.find(owner)
    if (planet == null) sender.sendMessage(
        messages["${prefix}HasNoLoadedPlanet"]
            ?.replacePlayer("Target", owner.uuid)
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
 * Current Version: 1.0 (28.02.2019 - 18.03.2019)
 */
fun generatePrice(size: Double) = Math.pow(size, config.planets.pow)


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 07:51.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
fun String?.replacePrice(planet: Planet) = this
    .replace("<Price>", planet.atmosphere.price)
    .replace("<NextPrice>", generatePrice(planet.atmosphere.size + 1.toDouble()))


///**
// * @author Lars Artmann | LartyHD
// * Created by Lars Artmann | LartyHD on 28.02.2019 09:15.
// * Current Version: 1.0 (28.02.2019 - 28.02.2019)
// */
//private inline fun CommandSender.hasPermissionBy(permissionKey: String, block: () -> Unit) =
//    if (hasPermission(this, permissions[permissionKey] ?: "")) block() else sendMessage(permissionMessage)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 10:07.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
fun String?.replace(prefix: String, planetLocation: PlanetLocation) = replace(
    prefix,
    planetLocation.vector,
    planetLocation.yaw,
    planetLocation.pitch/*,
    "PlanetID" to planetLocation.planetID*/
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.06.2019 11:36.
 * Current Version: 1.0 (19.06.2019 - 19.06.2019)
 */

fun Planet.toPlanetPlayer(request: (PlanetPlayer) -> Unit): Unit = load { loadedPlanet ->
    val player = owner.uuid.toPlayer() ?: return@load
    val planetPlayer = BasePlanetPlayer(player, loadedPlanet)

    players += planetPlayer
    request(planetPlayer)
}
