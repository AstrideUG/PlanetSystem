package me.devsnox.planetsystem.core.location;

import me.devsnox.planetsystem.api.location.Region;
import org.bukkit.Location;

public class BaseRegion implements Region {

    private final Location min;
    private final Location max;

    public BaseRegion(final Location min, final Location max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Location getMin() {
        return this.min;
    }

    @Override
    public Location getMax() {
        return this.max;
    }
}
