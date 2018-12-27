package me.devsnox.planetsystem.core.planet;

import com.boydti.fawe.object.schematic.Schematic;
import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BasePlanet implements Planet {

    private final UUID uniqueID;
    private final UUID ownerUniqueID;
    private final List<PlanetMember> members;
    private int size;
    private Location min;
    private Location max;
    private Location middle;
    private Location spawnLocation;

    public BasePlanet(final UUID uniqueID, final UUID ownerUniqueID, final List<PlanetMember> members, final int size, final Location min, final Location max, final Location middle, final Location spawnLocation) {
        this.uniqueID = uniqueID;
        this.ownerUniqueID = ownerUniqueID;
        this.members = members;
        this.size = size;
        this.min = min;
        this.max = max;
        this.middle = middle;
        this.spawnLocation = spawnLocation;
    }

    @Override
    public UUID getUniqueID() {
        return uniqueID;
    }

    @Override
    public UUID getOwnerUniqueID() {
        return ownerUniqueID;
    }

    @Override
    public List<PlanetMember> getMembers() {
        return members;
    }


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(final int size) {
        this.size = size;
    }

    @Override
    public Location getMin() {
        return min;
    }

    public void setMin(final Location min) {
        this.min = min;
    }

    @Override
    public Location getMax() {
        return max;
    }

    @Override
    public boolean isInside(final Location location) {
        return false;
    }

    public void setMax(final Location max) {
        this.max = max;
    }

    @Override
    public Location getMiddle() {
        return middle;
    }

    public void setMiddle(final Location middle) {
        this.middle = middle;
    }

    @Override
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public void setSpawnLocation(final Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    @Override
    public Schematic getSchematic() {
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BasePlanet that = (BasePlanet) o;
        return size == that.size &&
                uniqueID.equals(that.uniqueID) &&
                ownerUniqueID.equals(that.ownerUniqueID) &&
                members.equals(that.members) &&
                min.equals(that.min) &&
                max.equals(that.max) &&
                middle.equals(that.middle) &&
                spawnLocation.equals(that.spawnLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueID, ownerUniqueID, members, size, min, max, middle, spawnLocation);
    }

    @Override
    public String toString() {
        return "BasePlanet{" +
                "uniqueID=" + uniqueID +
                ", ownerUniqueID=" + ownerUniqueID +
                ", members=" + members +
                ", size=" + size +
                ", min=" + min +
                ", max=" + max +
                ", middle=" + middle +
                ", spawnLocation=" + spawnLocation +
                '}';
    }
}
