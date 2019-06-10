package de.astride.planetsystem.api.location

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.proxies.gameWorld
import de.astride.planetsystem.api.proxies.loadedPlanets
import lombok.NoArgsConstructor
import org.bukkit.Location
import org.bukkit.util.Vector

@NoArgsConstructor

data class PlanetLocation constructor(
//    @Transient
    var planetID: UniqueID?,
    var vector: Vector = Vector(),
    var yaw: Float = 0f,
    var pitch: Float = 0f
) {

    constructor() : this(null)

    constructor(planetID: UniqueID, location: Location) : this(
        planetID,
        location.toVector(),
        location.yaw,
        location.pitch
    )

    constructor(planet: LoadedPlanet, location: Location) : this(
        planet.uniqueID,
        location.clone().subtract(planet.middle).add(Vector(0.5, 0.0, 0.5))/* Location - middle-point */
    )

}

fun PlanetLocation.toBukkitLocation(input: Vector): Location =
    vector.clone().add(input).toLocation(gameWorld).also {
        it.yaw = yaw
        it.pitch = pitch
    }

fun PlanetLocation.toBukkitLocation(planet: LoadedPlanet): Location = toBukkitLocation(planet.middle.toVector())

fun PlanetLocation.toBukkitLocation(): Location? {
    val planet = loadedPlanets.find(planetID ?: return null) ?: return null
    return toBukkitLocation(planet)
}

//TODO Add to Darkness
fun Number.toBukkitVector() = toDouble().run { Vector(this, this, this) }
