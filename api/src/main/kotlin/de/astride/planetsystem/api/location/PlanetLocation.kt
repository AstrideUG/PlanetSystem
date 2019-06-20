/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.location

import de.astride.planetsystem.api.functions.BukkitVector
import de.astride.planetsystem.api.holder.gridHandler
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.inline.planet
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import org.bukkit.Location
import org.bukkit.util.Vector

data class PlanetLocation @JvmOverloads constructor(
//    @Transient
    var planetID: UniqueID? = null,
    var vector: BukkitVector = BukkitVector(),
    var yaw: Float = 0f,
    var pitch: Float = 0f
) {

    constructor(planetID: UniqueID, location: Location) : this(
        planetID,
        location.toVector(),
        location.yaw,
        location.pitch
    )

    constructor(planet: LoadedPlanet, location: Location) : this(
        planet.uniqueID,
        /* Location - middle-point */
        location.relativeTo(planet.middle)
    )

}

fun PlanetPlayer.relativeTo(): Vector = player.location.relativeTo(planet.middle).toVector()
fun Location.relativeTo(middle: Location): Location = clone().subtract(middle)

fun PlanetLocation.toBukkitLocation(input: Vector): Location =
    vector.clone().add(input).toLocation(gridHandler.world).also {
        it.yaw = yaw
        it.pitch = pitch
    }

fun PlanetLocation.toBukkitLocation(planet: LoadedPlanet): Location = toBukkitLocation(planet.middle.toVector())

fun PlanetLocation.toBukkitLocation(): Location? {
    val planet = planetID?.planet ?: return null
    return toBukkitLocation(planet)
}
