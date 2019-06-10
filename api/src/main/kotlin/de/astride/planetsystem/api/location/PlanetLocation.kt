package de.astride.planetsystem.api.location

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.util.Vector

data class PlanetLocation @JvmOverloads constructor(
//    @Transient
    var planetID: UniqueID? = null,
    var vector: Vector = Vector(),
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
        location.clone().subtract(planet.middle).apply {
            add(Vector(if (x < 0) -0.5 else 0.5, 0.0, if (y < 0) -0.5 else 0.5))
        }
    )

}

fun PlanetLocation.toBukkitLocation(input: Vector): Location =
    vector.clone().add(input).toLocation(Holder.instance.gridHandler.world).also {
        it.yaw = yaw
        it.pitch = pitch
    }

fun PlanetLocation.toBukkitLocation(planet: LoadedPlanet): Location = toBukkitLocation(planet.middle.toVector())

fun PlanetLocation.toBukkitLocation(): Location? {
    val planet = Holder.instance.loadedPlanets.find(planetID ?: return null) ?: return null
    return toBukkitLocation(planet)
}

//TODO Add to Darkness
fun Number.toBukkitVector() = toDouble().run { Vector(this, this, this) }
