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
import me.devsnox.planetsystem.core.utils.ThreadUtils;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public BaseLoadedPlanet(final Planet planet, final Location middle, final int maxSize) {
        super(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
        System.out.println("Init BaseLoadedPlanet");
        this.planet = planet;
        this.middle = middle;

        System.out.println(planet);
        System.out.println(middle);
        System.out.println(maxSize);

        final byte size = planet.getSize();
        System.out.println(size);
        final org.bukkit.util.Vector vSize = new org.bukkit.util.Vector(size, size, size);
        System.out.println(vSize);
        final org.bukkit.util.Vector vMaxSize = new org.bukkit.util.Vector(maxSize, maxSize, maxSize);
        System.out.println(vMaxSize);

        final PlanetLocation innerMin = PlanetLocation.create(vSize.clone().multiply(-1), middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        System.out.println(innerMin);
        final PlanetLocation innerMax = PlanetLocation.create(vSize.clone().subtract(new Vector(1, 1, 1)), middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        System.out.println(innerMax);

        ThreadUtils.sync(() -> {
            System.out.println(innerMax.toBukkitLocation(this));
            System.out.println(innerMin.toBukkitLocation(this));
            innerMin.toBukkitLocation(this).getBlock().setType(Material.REDSTONE_BLOCK);
            innerMax.toBukkitLocation(this).getBlock().setType(Material.REDSTONE_BLOCK);
            System.out.println(innerMin.toBukkitLocation(this));
        });


        final PlanetLocation outerMin = PlanetLocation.create(vMaxSize.clone().multiply(-1), middle.getYaw(), middle.getPitch(), planet.getUniqueID());
        final PlanetLocation outerMax = PlanetLocation.create(vMaxSize, middle.getYaw(), middle.getPitch(), planet.getUniqueID());

        this.inner = new BaseRegion(innerMin, innerMax);
        this.outer = new BaseRegion(outerMin, outerMax);
        System.out.println("Inits BaseLoadedPlanet");
    }

    @Override
    public Location getMiddle() {
        System.out.println("Start getMiddle()");
        System.out.println("middle: " + middle);
        System.out.println("Ends getMiddle()");
        return middle.clone();
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
