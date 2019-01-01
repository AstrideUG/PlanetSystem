package me.devsnox.planetsystem.core.holder.data;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.location.Region;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.database.DatabasePlanet;
import me.devsnox.planetsystem.core.planet.BaseLoadedPlanet;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public final class PlanetDataImpl implements PlanetData {

    private final Holder holder;
    private final DynamicNetworkAPI dynamicNetworkAPI;
    private final Set<LoadedPlanet> loadedPlanets;

    public PlanetDataImpl(final Holder holder) {
        this.holder = holder;
        this.dynamicNetworkAPI = DynamicNetworkFactory.dynamicNetworkAPI;
        this.loadedPlanets = new HashSet<>();
    }

    @Override
    public void load(final UUID owner, final Consumer<LoadedPlanet> request) {
        final Planet planet = holder.getDatabaseHandler().getPlanet(owner).toPlanet();

        final GridHandler grid = holder.getGridHandler();
        final Location location = grid.getEmptyLocation();
        System.out.println(grid.getEmptyLocation());
        final BaseLoadedPlanet loadedPlanet = new BaseLoadedPlanet(planet, location, grid.getMaxSize());
        loadedPlanets.add(loadedPlanet);

        System.out.println(dynamicNetworkAPI);
        this.dynamicNetworkAPI.getSchematic(planet.getUniqueID(), schematic ->
                schematic.paste(FaweAPI.getWorld(location.getWorld().getName()), new Vector(location.getX(), location.getY(), location.getZ())));

        final Region inner = loadedPlanet.getInner();
        System.out.println(inner.getMin().toBukkitLocation(loadedPlanet));
        System.out.println(inner.getMax().toBukkitLocation(loadedPlanet));
        inner.getMin().toBukkitLocation(loadedPlanet).getBlock().setType(Material.REDSTONE_BLOCK);
        inner.getMax().toBukkitLocation(loadedPlanet).getBlock().setType(Material.REDSTONE_BLOCK);
        System.out.println(inner.getMax().toBukkitLocation(loadedPlanet));
        System.out.println(inner.getMin().toBukkitLocation(loadedPlanet));

        final org.bukkit.util.Vector midpoint = inner.getMax().getVector().clone().midpoint(inner.getMin().getVector());
        new PlanetLocation(loadedPlanet.getUniqueID(), midpoint, 0, 0).toBukkitLocation(loadedPlanet).getBlock().setType(Material.BEACON);
        ;

        request.accept(loadedPlanet);
    }

    @Override
    public void save(final UUID owner) {
        final LoadedPlanet loadedPlanet = holder.getPlanetData().getLoadedPlanet(owner);
        if (loadedPlanet == null) return;
        final DatabasePlanet databasePlanet = DatabasePlanet.by(loadedPlanet);

        this.dynamicNetworkAPI.saveSchematic(owner, loadedPlanet.getSchematic());
        this.holder.getDatabaseHandler().savePlanet(databasePlanet);
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return loadedPlanets;
    }

}
