package de.astride.planetsystem.core.location

import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.Region
import lombok.Data
import org.bukkit.util.Vector

@Data
class BaseRegion(min: PlanetLocation, max: PlanetLocation) : Region {

    override val min: PlanetLocation
    override val max: PlanetLocation

    init {
        if (min.planetID != max.planetID) throw IllegalArgumentException("min and max must have the save planetID")

        val p1 = min.vector
        val p2 = max.vector

        val minX = Math.min(p1.x, p2.x)
        val minY = Math.min(p1.y, p2.y)
        val minZ = Math.min(p1.z, p2.z)

        val maxX = Math.max(p1.x, p2.x)
        val maxY = Math.max(p1.y, p2.y)
        val maxZ = Math.max(p1.z, p2.z)

        this.min = PlanetLocation(min.planetID, Vector(minX, minY, minZ))
        this.max = PlanetLocation(max.planetID, Vector(maxX, maxY, maxZ))

    }

}
