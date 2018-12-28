package me.devsnox.planetsystem.core.planet;

import com.boydti.fawe.object.schematic.Schematic;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.location.Region;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class BaseLoadedPlanet implements LoadedPlanet {

    private final Planet planet;

    private final Region inner;
    private final Region outer;

    private final Location middle;

    public BaseLoadedPlanet(final Planet planet, final Location middle, final int maxSize) {
        this.planet = planet;
        this.inner = null; //TODO: Calculate inner region with planet size
        this.outer = null; //TODO: Calculate outer region with maxSize
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
}
