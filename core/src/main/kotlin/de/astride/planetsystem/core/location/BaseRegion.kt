/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.location

import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.Region
import org.bukkit.util.Vector
import kotlin.math.max
import kotlin.math.min

class BaseRegion(min: PlanetLocation, max: PlanetLocation) : Region {

    override val min: PlanetLocation
    override val max: PlanetLocation

    init {
        require(min.planetID == max.planetID) { "min and max must have the save planetID" }

        val p1 = min.vector
        val p2 = max.vector

        val minX = min(p1.x, p2.x)
        val minY = min(p1.y, p2.y)
        val minZ = min(p1.z, p2.z)

        val maxX = max(p1.x, p2.x)
        val maxY = max(p1.y, p2.y)
        val maxZ = max(p1.z, p2.z)

        this.min = PlanetLocation(min.planetID, Vector(minX, minY, minZ))
        this.max = PlanetLocation(max.planetID, Vector(maxX, maxY, maxZ))

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseRegion) return false

        if (min != other.min) return false
        if (max != other.max) return false

        return true
    }

    override fun hashCode(): Int {
        var result = min.hashCode()
        result = 31 * result + max.hashCode()
        return result
    }

    override fun toString(): String = "BaseRegion(min=$min, max=$max)"

}
