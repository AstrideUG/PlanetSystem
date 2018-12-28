package me.devsnox.planetsystem.core;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.PlanetFactory;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.api.InternalHandler;
import me.devsnox.planetsystem.core.cache.CacheHandler;
import me.devsnox.planetsystem.core.config.ConfigHandler;
import me.devsnox.planetsystem.core.database.DatabaseHandler;
import me.devsnox.planetsystem.core.database.DatabasePlayer;
import me.devsnox.planetsystem.core.planet.BaseLoadedPlanet;
import me.devsnox.planetsystem.core.player.BasePlanetPlayer;
import me.devsnox.planetsystem.core.world.GridHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BaseInternalHandler implements InternalHandler {

    private final Plugin plugin;

    private final DynamicNetworkAPI dynamicNetworkAPI;

    private final ConfigHandler configHandler;
    private final CacheHandler cacheHandler;
    private final DatabaseHandler databaseHandler;
    private final GridHandler gridHandler;

    private final SaveTask saveTask;

    public BaseInternalHandler(final Plugin plugin) {
        this.plugin = plugin;

        this.dynamicNetworkAPI = DynamicNetworkFactory.dynamicNetworkAPI;

        this.configHandler = new ConfigHandler();
        this.cacheHandler = new CacheHandler();
        this.databaseHandler = new DatabaseHandler();
        this.gridHandler = new GridHandler("PlanetWorld", 2048); //TODO: Add config handling

        this.saveTask = new SaveTask();
        this.saveTask.runTaskTimer(this.plugin, 0, 20 * 60);
    }

    public BasePlanetPlayer loadPlayer(final Player player,) {
        final DatabasePlayer databasePlayer = this.databaseHandler.getPlayer(player.getUniqueId());

        PlanetFactory.planetAPI.getPlanet(player.getUniqueId()).load(loadedPlanet -> {
            final List<Planet> planetList = new ArrayList<>();

            databasePlayer.getMemberedPlanets().forEach(uuid -> planetList.add(PlanetFactory.planetAPI.getPlanet(uuid)));
        });
    }

    public void savePlayer(final UUID uuid) {
        databaseHandler.savePlayer(((BasePlanetPlayer) planetPlayer).toDatabasePlayer());
    }


    public boolean isPlanetLoaded(final UUID uuid) {
        return this.cacheHandler.getPlanetCache().containsKey(uuid);
    }

    public boolean isPlanetLoaded(final Player player) {
        return isPlanetLoaded(this.getPlanetIdByPlayerUUID(player.getUniqueId()));
    }

    public LoadedPlanet getLoadedPlanet(final UUID planetId) {
        return this.cacheHandler.getPlanetCache().get(planetId);
    }

    public LoadedPlanet getLoadedPlanet(final Player player) {
        return this.getLoadedPlanet(this.getPlanetIdByPlayerUUID(player.getUniqueId()));
    }

    public void loadPlanet(final UUID planetId) {
        final Planet planet = this.databaseHandler.getPlanet(planetId).toBasePlanet();
        this.preparePlanet(planet, loadedPlanet -> cacheHandler.getPlanetCache().put(loadedPlanet.getUniqueID(), loadedPlanet));
    }

    public void loadPlanetByPlayerId(final UUID uuid) {
        if (this.cacheHandler.getPlayerCache().containsKey(uuid)) {
            this.loadPlanet(getPlanetIdByPlayerUUID(uuid));
        }
    }

    public void savePlanet(final UUID planetId) {
        this.dynamicNetworkAPI.saveSchematic(planetId, this.cacheHandler.getPlanetCache().get(planetId).getSchematic());
    }

    public void savePlanet(final Player player) {
        this.savePlanet(this.cacheHandler.getPlayerCache().get(player.getUniqueId()).getPlanet().getUniqueID());
    }

    private void preparePlanet(final Planet planet, final Consumer<LoadedPlanet> result) {
        this.dynamicNetworkAPI.getSchematic(planet.getUniqueID(), schematic -> {
            final Location location = gridHandler.getEmptyLocation();
            schematic.paste(FaweAPI.getWorld(location.getWorld().getName()), new Vector(location.getX(), location.getY(), location.getZ()));

            result.accept(new BaseLoadedPlanet(planet, location, gridHandler.getMaxSize()));
        });
    }

    private UUID getPlanetIdByPlayerUUID(UUID uuid) {
        if (this.cacheHandler.getPlayerCache().containsKey(uuid)) {
            return this.cacheHandler.getPlayerCache().get(uuid).getPlanet().getUniqueID();
        }
        throw new NullPointerException(); //TODO: Custom exception
    }

    @Override
    public void autoLoadPlayer(UUID uuid) {
        cacheHandler.getPlayerCache().put(player.getUniqueId(), new BasePlanetPlayer(player, loadedPlanet, planetList));
    }

    @Override
    public void autoSavePlayer(UUID uuid) {

    }

    @Override
    public boolean isPlanetLoadedByPlanetId(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlanetLoadedByPlayerId(UUID uuid) {
        return false;
    }

    @Override
    public LoadedPlanet getLoadedPlanetByPlanetId() {
        return null;
    }

    @Override
    public LoadedPlanet getLoadedPlanetByPlayerId(UUID uuid) {
        return null;
    }

    @Override
    public void autoLoadPlanetByPlayerId(UUID uuid) {

    }

    @Override
    public void autoSavePlanetByPlayerId(UUID uuid) {

    }

    private class SaveTask extends BukkitRunnable {

        @Override
        public void run() {
            if (cacheHandler.getPlanetCache().size() != 0) {
                cacheHandler.getPlanetCache().forEach((uuid, planet) -> dynamicNetworkAPI.saveSchematic(uuid, planet.getSchematic()));
            } else if (cacheHandler.getPlayerCache().size() != 0) {
                cacheHandler.getPlayerCache().forEach((uuid, planetPlayer) -> {
                    databaseHandler.savePlayer(((BasePlanetPlayer) planetPlayer).toDatabasePlayer());
                });
            }
        }
    }
}
