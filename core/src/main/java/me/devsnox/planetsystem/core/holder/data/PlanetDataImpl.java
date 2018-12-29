package me.devsnox.planetsystem.core.holder.data;

import com.sk89q.worldedit.WorldEditException;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.database.DatabasePlanet;
import me.devsnox.planetsystem.core.planet.BaseLoadedPlanet;
import org.bukkit.Location;

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

        this.dynamicNetworkAPI.getSchematic(planet.getUniqueID(), schematic -> {
            final GridHandler grid = holder.getGridHandler();
            final Location location = grid.getEmptyLocation();
            final BaseLoadedPlanet loadedPlanet = new BaseLoadedPlanet(planet, location, grid.getMaxSize());

            try {
                schematic.paste(location);
            } catch (final WorldEditException e) {
                e.printStackTrace();
            }

            holder.getPlanetData().getLoadedPlanets().add(loadedPlanet);
            request.accept(loadedPlanet);
        });
    }

    @Override
    public void save(final UUID owner) {
        final LoadedPlanet loadedPlanet = holder.getPlanetData().getLoadedPlanet(owner);
        if (loadedPlanet == null) return;
        final DatabasePlanet databasePlanet = DatabasePlanet.by(loadedPlanet);

        //TODO: Save Method!!! //this.dynamicNetworkAPI.saveSchematic(owner, loadedPlanet.getSchematic());
        this.holder.getDatabaseHandler().savePlanet(databasePlanet);
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return loadedPlanets;
    }

}
