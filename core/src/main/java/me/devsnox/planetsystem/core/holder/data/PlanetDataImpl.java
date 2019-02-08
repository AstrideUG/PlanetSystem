package me.devsnox.planetsystem.core.holder.data;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.database.DatabasePlanet;
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

        this.dynamicNetworkAPI.saveSchematic(databasePlanet.getUuid(), loadedPlanet.getSchematic());
        this.holder.getDatabaseHandler().savePlanet(databasePlanet);

    }

    @Override
    public void unload(UUID owner) {
        this.save(owner);
        LoadedPlanet planet = this.holder.getPlayerData().getPlayer(owner).getPlanet();

        GridHandler grid = this.holder.getGridHandler();
        grid.removeEntry(grid.getId(planet.getMiddle()));

        CuboidRegion cuboidRegion = new CuboidRegion(FaweAPI.getWorld(Holder.Impl.holder.getWorld().getName()), planet.getOuter().getMin().toWEVector(), planet.getOuter().getMax().toWEVector());

        EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(Holder.Impl.holder.getWorld().getName())).fastmode(true).build();

        try {
            editSession.setBlocks(cuboidRegion, new BaseBlock(Material.AIR.getId()));
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }

        editSession.flushQueue();

        this.loadedPlanets.remove(planet);
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return this.loadedPlanets;
    }

}
