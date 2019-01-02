package me.devsnox.planetsystem.core.holder.data;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.location.PlanetLocation;
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

    public PlanetDataImpl(Holder holder) {
        this.holder = holder;
        this.dynamicNetworkAPI = DynamicNetworkFactory.dynamicNetworkAPI;
        this.loadedPlanets = new HashSet<>();
    }

    @Override
    public void load(UUID owner, Consumer<LoadedPlanet> request) {
        System.out.println("load Schem" + owner);

        for (LoadedPlanet loadedPlanet : this.loadedPlanets)
            if (loadedPlanet.getOwnerUniqueID() == owner) {
                System.out.println("allreaY LOADED");
                request.accept(loadedPlanet);
                return;
            }

        Planet planet = this.holder.getDatabaseHandler().getPlanet(owner).toPlanet();

        GridHandler grid = this.holder.getGridHandler();
        Location location = grid.getEmptyLocation();
        BaseLoadedPlanet loadedPlanet = new BaseLoadedPlanet(planet, location, grid.getMaxSize());
        this.dynamicNetworkAPI.getSchematic(planet.getUniqueID(), schematic -> {
            System.out.println("schem" + owner);
            System.out.println(schematic.getClipboard().getRegion());
            System.out.println(schematic.getClipboard().getEntities());
            System.out.println(schematic.getClipboard().getOrigin());
            System.out.println(schematic.getClipboard().getDimensions());
            schematic.paste(FaweAPI.getWorld(location.getWorld().getName()), new Vector(location.getX(), location.getY(), location.getZ()));
        });
        this.loadedPlanets.add(loadedPlanet);


        PlanetLocation min = loadedPlanet.getInner().getMin();
        PlanetLocation max = loadedPlanet.getInner().getMax();

        org.bukkit.util.Vector maxV = max.getVector().clone().add(new org.bukkit.util.Vector(1, 0, 1));
        org.bukkit.util.Vector midpoint = max.getVector().clone().midpoint(min.getVector());

        PlanetLocation midpointLocation = new PlanetLocation(loadedPlanet.getUniqueID(), midpoint, 0, 0);
        PlanetLocation maxLocation = new PlanetLocation(max.getPlanetID(), maxV, max.getYaw(), max.getPitch());


        min.toBukkitLocation(loadedPlanet).getBlock().setType(Material.REDSTONE_BLOCK);
        maxLocation.toBukkitLocation(loadedPlanet).getBlock().setType(Material.REDSTONE_BLOCK);
        midpointLocation.toBukkitLocation(loadedPlanet).getBlock().setType(Material.BEACON);

        request.accept(loadedPlanet);
        System.out.println("finish load Schem" + owner);
    }

    @Override
    public void save(UUID owner) {
        System.out.println();
        System.out.println();
        System.out.println("Save Schem" + owner);
        System.out.println();
        System.out.println();
        LoadedPlanet loadedPlanet = this.holder.getPlanetData().getLoadedPlanetByOwner(owner);
        System.out.println(loadedPlanet);
        if (loadedPlanet == null) return;
        DatabasePlanet databasePlanet = DatabasePlanet.by(loadedPlanet);
        System.out.println(databasePlanet);

        this.dynamicNetworkAPI.saveSchematic(owner, loadedPlanet.getSchematic());
        this.holder.getDatabaseHandler().savePlanet(databasePlanet);
        Location min = loadedPlanet.getInner().getMin().toBukkitLocation();
        Location max = loadedPlanet.getInner().getMax().toBukkitLocation();

        for (int x = min.getBlockX(); x < max.getBlockX(); x++)
            for (int y = min.getBlockY(); y < max.getBlockY(); y++)
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++)
                    new Location(Holder.Impl.holder.getWorld(), x, y, z).getBlock().setType(Material.AIR);

    }

    @Override
    public void unload(UUID owner) {
        this.save(owner);
        GridHandler grid = this.holder.getGridHandler();
        LoadedPlanet planet = this.holder.getPlayerData().getPlayer(owner).getPlanet();
        grid.removeEntry(grid.getId(planet.getMiddle()));
        this.loadedPlanets.remove(planet);
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return this.loadedPlanets;
    }

}
