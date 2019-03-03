package de.astride.planetsystem.api.location

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.planet.LoadedPlanet
import lombok.NoArgsConstructor
import org.bukkit.Location
import org.bukkit.util.Vector
import xyz.morphia.annotations.Transient

@NoArgsConstructor
data class PlanetLocation(
    @Transient
    var planetID: UniqueID,
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

    constructor(planet: LoadedPlanet, location: Location) :
            this(planet.uniqueID, location.clone().subtract(planet.middle) /* Location - middle-point */)

}

fun PlanetLocation.toBukkitLocation(input: Vector): Location =
    vector.clone().add(input).toLocation(Holder.instance.gridHandler.world).also {
        it.yaw = yaw
        it.pitch = pitch
    }

fun PlanetLocation.toBukkitLocation(): Location? {
    val toVector = Holder.instance.find(planetID)?.middle?.toVector() ?: return null
    return toBukkitLocation(toVector)
}
