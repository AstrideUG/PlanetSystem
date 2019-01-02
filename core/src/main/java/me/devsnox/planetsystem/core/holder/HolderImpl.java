package me.devsnox.planetsystem.core.holder;

import lombok.Data;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.holder.data.PlayerData;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.core.PlanetSystem;
import me.devsnox.planetsystem.core.database.DatabaseHandler;
import me.devsnox.planetsystem.core.holder.data.PlanetDataImpl;
import me.devsnox.planetsystem.core.holder.data.PlayerDataImpl;
import me.devsnox.planetsystem.core.world.GridHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

@Data
public class HolderImpl implements Holder {

    private final Set<LoadedPlanet> loadedPlanets;
    private final PlayerData playerData;
    private final PlanetData planetData;

    private final DatabaseHandler databaseHandler;
    private final GridHandler gridHandler;

    public HolderImpl(final PlanetSystem javaPlugin) {
        this.loadedPlanets = new HashSet<>();
        this.playerData = new PlayerDataImpl(this);
        this.planetData = new PlanetDataImpl(this);

        this.databaseHandler = new DatabaseHandler();
        this.gridHandler = new GridHandler("PlanetWorld", 2048); //TODO: Add config handling

        Bukkit.getScheduler().runTaskTimer(javaPlugin, javaPlugin::saveAll, 0, 20 * 60);
        //Runtime.getRuntime().addShutdownHook(new Thread(this::saveAll));
    }

    @Override
    public World getWorld() {
        return this.gridHandler.getWorld();
    }

}
