package me.devsnox.planetsystem.core.planet;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.schematic.Schematic;
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
import org.bukkit.util.Vector;

import java.util.UUID;
import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseLoadedPlanet extends BasePlanet implements LoadedPlanet {

    private final Planet planet;
    private final Region inner;
    private final Region outer;
    private final Location middle;

    public BaseLoadedPlanet(Planet planet, Location middle, int maxSize) {
        super(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
        this.planet = planet;
        this.middle = middle;

        byte size = planet.getSize();
        org.bukkit.util.Vector vSize = new org.bukkit.util.Vector(size, size, size);
        org.bukkit.util.Vector vMaxSize = new org.bukkit.util.Vector(maxSize, maxSize, maxSize);

        PlanetLocation innerMin = PlanetLocation.create(vSize.clone().multiply(-1)/*.add(new Vector(0, 1, 0))*/, middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        PlanetLocation innerMax = PlanetLocation.create(vSize.clone().subtract(new Vector(1, 1, 1)), middle.getYaw(), middle.getPitch(), planet.getUniqueID());

        PlanetLocation outerMin = PlanetLocation.create(vMaxSize.clone().multiply(-1), middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        PlanetLocation outerMax = PlanetLocation.create(vMaxSize, middle.getYaw(), middle.getPitch(), planet.getUniqueID());

        this.inner = new BaseRegion(innerMin, innerMax);
        this.outer = new BaseRegion(outerMin, outerMax);
    }

    @Override
    public Location getMiddle() {
        return this.middle.clone();
    }

    @Override
    public Schematic getSchematic() {
        Schematic schematic = new Schematic(new CuboidRegion(FaweAPI.getWorld(Holder.Impl.holder.getWorld().getName()), this.inner.getMin().toWEVector(), this.inner.getMax().toWEVector()));
        System.out.println(schematic);
        System.out.println(schematic.getClipboard());
        System.out.println(schematic.getClipboard().getDimensions());
        System.out.println(schematic.getClipboard().getOrigin());
        System.out.println(schematic.getClipboard().getRegion());
        System.out.println(schematic.getClipboard().getEntities());

        //schematic.getClipboard().setOrigin(new com.sk89q.worldedit.Vector(this.middle.getX(), this.middle.getY(), this.middle.getZ()));

        return schematic;
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
    public PlanetLocation getSpawnLocation() {
        return this.planet.getSpawnLocation();
    }

    @Override
    public void setSpawnLocation(PlanetLocation planetLocation) {
        this.planet.setSpawnLocation(planetLocation);
    }

    @Override
    public void load(Consumer<LoadedPlanet> result) {
        result.accept(this);
    }

}
