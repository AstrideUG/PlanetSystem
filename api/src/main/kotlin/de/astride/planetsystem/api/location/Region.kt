package de.astride.planetsystem.api.location

import org.bukkit.util.Vector

interface Region {

    val min: PlanetLocation
    val max: PlanetLocation

}

fun Region.isInside(vector: Vector): Boolean = vector.isInAABB(min.vector, max.vector)
fun Region.isInside(location: PlanetLocation): Boolean = isInside(location.vector)
