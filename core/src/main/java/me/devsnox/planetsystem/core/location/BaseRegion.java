package me.devsnox.planetsystem.core.location;

import me.devsnox.planetsystem.api.location.Region;
import org.bukkit.Location;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseRegion that = (BaseRegion) o;
        return Objects.equals(min, that.min) &&
                Objects.equals(max, that.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "BaseRegion{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
