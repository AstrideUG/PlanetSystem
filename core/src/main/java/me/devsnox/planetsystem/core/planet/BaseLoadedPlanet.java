package me.devsnox.planetsystem.core.planet;

import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.location.Region;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.location.BaseRegion;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseLoadedPlanet extends BasePlanet implements LoadedPlanet {

    private final Planet planet;
    private final Region inner;
    private final Region outer;
    private final Location middle;

    public BaseLoadedPlanet(final Planet planet, final Location middle, final int maxSize) {
        super(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
        this.planet = planet;
        this.middle = middle;

        final byte size = planet.getSize();
        final org.bukkit.util.Vector vSize = new org.bukkit.util.Vector(size, size, size);
        final org.bukkit.util.Vector vMaxSize = new org.bukkit.util.Vector(maxSize, maxSize, maxSize);

        final PlanetLocation innerMin = PlanetLocation.create(vSize.clone().multiply(-1), middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        final PlanetLocation innerMax = PlanetLocation.create(vSize, middle.getYaw(), middle.getPitch(), planet.getUniqueID());

        final PlanetLocation outerMin = PlanetLocation.create(vMaxSize.clone().multiply(-1), middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        final PlanetLocation outerMax = PlanetLocation.create(vMaxSize, middle.getYaw(), middle.getPitch(), planet.getUniqueID());

        this.inner = new BaseRegion(innerMin, innerMax);
        this.outer = new BaseRegion(outerMin, outerMax);
    }

    @Override
    public Location getMiddle() {
        return middle.clone();
    }

    public org.bukkit.util.Vector getMiddleVector() {
        return middle.toVector();
    }

    @Override
    public Schematic getSchematic() {
        return new Schematic(new CuboidRegion(new BukkitWorld(Holder.Impl.holder.getWorld()), inner.getMin().toWEVector(), inner.getMax().toWEVector()));
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
