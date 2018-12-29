package me.devsnox.planetsystem.core.planet;

import com.boydti.fawe.object.schematic.Schematic;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.location.Region;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.location.BaseRegion;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

//TODO: Improve BasePlanet / Planet usage
public class BaseLoadedPlanet extends BasePlanet implements LoadedPlanet {

    private final Planet planet;

    private final Region inner;
    private final Region outer;

    private final Location middle;

    public BaseLoadedPlanet(final Planet planet, final Location middle, final int maxSize) {
        super(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
        this.planet = planet;
        final byte size = planet.getSize();
        this.inner = new BaseRegion(middle.clone().subtract(size, size, size), middle.clone().add(size, size, size));
        this.outer = new BaseRegion(middle.clone().subtract(maxSize, maxSize, maxSize), middle.clone().add(maxSize, maxSize, maxSize));
        this.middle = middle;
    }


    @Override
    public Region getInner() {
        return inner;
    }

    @Override
    public Region getOuter() {
        return outer;
    }

    @Override
    public Location getMiddle() {
        return middle;
    }

    @Override
    public Schematic getSchematic() {
        return null; //TODO: Create schematic
    }

    @Override
    public UUID getUniqueID() {
        return this.planet.getUniqueID();
    }

    @Override
    public UUID getOwnerUniqueID() {
        return this.planet.getOwnerUniqueID();
    }

    @Override
    public String getName() {
        return this.planet.getName();
    }

    @Override
    public List<UUID> getMembers() {
        return this.planet.getMembers();
    }

    @Override
    public byte getSize() {
        return this.planet.getSize();
    }

    @Override
    public void setSize(final byte size) {
        this.planet.setSize(size);
    }

    @Override
    public PlanetLocation getSpawnLocation() {
        return this.planet.getSpawnLocation();
    }

    @Override
    public void setSpawnLocation(final PlanetLocation planetLocation) {
        this.planet.setSpawnLocation(planetLocation);
    }

    @Override
    public void load(final Consumer<LoadedPlanet> result) {
        result.accept(this);
    }

    @Override
    public String toString() {
        return "BaseLoadedPlanet{" +
                "planet=" + planet +
                ", inner=" + inner +
                ", outer=" + outer +
                ", middle=" + middle +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final BaseLoadedPlanet that = (BaseLoadedPlanet) o;
        return Objects.equals(planet, that.planet) &&
                Objects.equals(inner, that.inner) &&
                Objects.equals(outer, that.outer) &&
                Objects.equals(middle, that.middle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), planet, inner, outer, middle);
    }
}
