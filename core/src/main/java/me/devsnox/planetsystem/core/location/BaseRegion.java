package me.devsnox.planetsystem.core.location;

import lombok.Data;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.location.Region;
import org.bukkit.util.Vector;

@Data
public class BaseRegion implements Region {

    private final PlanetLocation min;
    private final PlanetLocation max;

    public BaseRegion(final PlanetLocation min, final PlanetLocation max) {
        if (!min.getPlanetID().equals(max.getPlanetID()))
            throw new IllegalArgumentException("min and max must have the save planetID");

        final Vector p1 = min.getVector();
        final Vector p2 = max.getVector();

        final double minX = Math.min(p1.getX(), p2.getX());
        final double minY = Math.min(p1.getY(), p2.getY());
        final double minZ = Math.min(p1.getZ(), p2.getZ());

        final double maxX = Math.max(p1.getX(), p2.getX());
        final double maxY = Math.max(p1.getY(), p2.getY());
        final double maxZ = Math.max(p1.getZ(), p2.getZ());

        this.min = new PlanetLocation(min.getPlanetID(), new Vector(minX, minY, minZ), 0, 0);
        this.max = new PlanetLocation(max.getPlanetID(), new Vector(maxX, maxY, maxZ), 0, 0);

    }

}
