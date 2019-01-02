package me.devsnox.planetsystem.core.holder.data;

import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.database.DatabasePlanet;
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
        Planet planet = this.holder.getDatabaseHandler().getPlanet(owner).toPlanet();
        planet.load(request);
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

    }

    @Override
    public void unload(UUID owner) {
        this.save(owner);
        LoadedPlanet planet = this.holder.getPlayerData().getPlayer(owner).getPlanet();

        GridHandler grid = this.holder.getGridHandler();
        grid.removeEntry(grid.getId(planet.getMiddle()));

        Location min = planet.getInner().getMin().toBukkitLocation().add(1, 1, 1);
        System.out.println(min);
        Location max = planet.getInner().getMax().toBukkitLocation();
        System.out.println(max);
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++)
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++)
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++)
                    new Location(Holder.Impl.holder.getWorld(), x, y, z).getBlock().setType(Material.AIR);

        this.loadedPlanets.remove(planet);
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return this.loadedPlanets;
    }

}
