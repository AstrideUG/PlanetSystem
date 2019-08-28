/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

@file:Suppress("DEPRECATION")

package de.astride.planetsystem.core.functions

import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.patterns.Pattern
import com.sk89q.worldedit.patterns.SingleBlockPattern
import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.database.OfflinePlayer
import de.astride.planetsystem.api.holder.players
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.loadedPlanet
import de.astride.planetsystem.api.proxies.planet
import de.astride.planetsystem.core.player.BasePlanetPlayer
import de.astride.planetsystem.core.proxies.config
import de.astride.planetsystem.core.utils.FaweUtils
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayer
import org.bukkit.Location
import org.bukkit.command.CommandSender
import kotlin.math.pow

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 00:43.
 */

//TODO Rename
fun LoadedPlanet.place(
    location: Location = middle,
    size: Double = atmosphere.size.toDouble(),
    pattern: Pattern = SingleBlockPattern(BaseBlock(atmosphere.blockID, atmosphere.blockDamage))
): Unit = FaweUtils.setCuboid(location, size, pattern)


fun LoadedPlanet.delete(
    location: Location = middle,
    size: Double = atmosphere.size.toDouble()
): Unit = FaweUtils.setCuboid(location, size, SingleBlockPattern(BaseBlock(0)))


//TODO make logging better (AOP?)!
fun findPlanetOrMessage(
    owner: Owner,
    sender: CommandSender,
    byTarget: Boolean = false
): LoadedPlanet? {
    val prefix = if (byTarget) "Target" else "Player"
    val planet = owner.loadedPlanet
    if (planet == null) sender.sendMessage(
        messages["${prefix}HasNoLoadedPlanet"]
            ?.replacePlayer("Target", owner.uuid)
            ?.replace("<Sender>", sender.name, true)
    )
    return planet
}


val Atmosphere.price get() = generatePrice(size.toDouble())

fun generatePrice(size: Double): Double = size.pow(config.planets.pow)

fun String?.replacePrice(planet: OfflinePlanet) = this
    .replace("<Price>", planet.atmosphere.price)
    .replace("<NextPrice>", generatePrice(planet.atmosphere.size + 1.toDouble()))

fun String?.replace(prefix: String, planetLocation: PlanetLocation) = replace(
    prefix,
    planetLocation.vector,
    planetLocation.yaw,
    planetLocation.pitch/*,
    "PlanetID" to spawnLocation.planetID*/
)

fun OfflinePlayer.load(planet: OfflinePlanet? = planetUniqueId.planet): PlanetPlayer? {

    if (this is PlanetPlayer) {
        players += this
        return this
    }

    val loadedPlanet = planet?.load() ?: return null
    val player = owner.uuid.toPlayer() ?: return null
    val planetPlayer = BasePlanetPlayer(player, loadedPlanet, history)

    players += planetPlayer
    return planetPlayer
}
