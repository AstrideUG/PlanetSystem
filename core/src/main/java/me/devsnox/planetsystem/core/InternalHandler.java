package me.devsnox.planetsystem.core;

import jdk.internal.jline.internal.Nullable;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.cache.CacheHandler;
import me.devsnox.planetsystem.core.config.ConfigHandler;
import me.devsnox.planetsystem.core.database.DatabaseHandler;
import me.devsnox.planetsystem.core.world.GridHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InternalHandler {

    private final Plugin plugin;

    private final DynamicNetworkAPI dynamicNetworkAPI;

    private final ConfigHandler configHandler;
    private final CacheHandler cacheHandler;
    private final DatabaseHandler databaseHandler;
    private final GridHandler gridHandler;

    private final SaveTask saveTask;

    public InternalHandler(final Plugin plugin) {
        this.plugin = plugin;

        this.dynamicNetworkAPI = DynamicNetworkFactory.dynamicNetworkAPI;

        this.configHandler = new ConfigHandler();
        this.cacheHandler = new CacheHandler();
        this.databaseHandler = new DatabaseHandler();
        this.gridHandler = new GridHandler("PlanetWorld", 2048); //TODO: Add config handling

        this.saveTask = new SaveTask();
        this.saveTask.runTaskTimer(this.plugin, 0, 20 * 60);
    }

    public boolean isPlanetLoaded(UUID uuid) {
        return this.cacheHandler.getPlanetCache().containsKey(uuid);
    }

    @Nullable
    public LoadedPlanet getPlanet() {

    }

    public LoadedPlanet getLoadedPlanet(UUID uuid) {
        if (this.cacheHandler.getPlanetCache().containsKey())
    }

    public void loadPlanet(final UUID planetId) {
        this.dynamicNetworkAPI.getSchematic(planetId, schematic -> {

        });
    }

    public void savePlanet(final UUID planetId) {
        this.dynamicNetworkAPI.saveSchematic(planetId, this.cacheHandler.getPlanetCache().get(planetId).getSchematic());
    }

    private void preparePlanet(Planet planet) {

    }


    private class SaveTask extends BukkitRunnable {

        @Override
        public void run() {
            if (cacheHandler.getPlanetCache().size() != 0) {
                cacheHandler.getPlanetCache().forEach((uuid, planet) -> dynamicNetworkAPI.saveSchematic(uuid, planet.getSchematic()));
            }

            if (cacheHandler.getPlayerCache().size() != 0) {
                databaseHandler.savePlayer();
            }
        }
    }
}
