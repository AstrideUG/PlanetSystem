package me.devsnox.planetsystem.core;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.api.InternalHandler;
import me.devsnox.planetsystem.core.cache.CacheHandler;
import me.devsnox.planetsystem.core.config.ConfigHandler;
import me.devsnox.planetsystem.core.database.DatabaseHandler;
import me.devsnox.planetsystem.core.database.DatabasePlayer;
import me.devsnox.planetsystem.core.planet.BaseLoadedPlanet;
import me.devsnox.planetsystem.core.planet.BasePlanet;
import me.devsnox.planetsystem.core.player.BasePlanetPlayer;
import me.devsnox.planetsystem.core.world.GridHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
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

    public void loadPlayer(final UUID uuid, final Consumer<PlanetPlayer> request) {
        final DatabasePlayer databasePlayer = this.databaseHandler.getPlayer(uuid);

        this.loadPlanetByPlanetId(databasePlayer.getPlanetUniqueId(), loadedPlanet -> {
            final List<Planet> planetList = new ArrayList<>();

            final PlanetPlayer planetPlayer = new BasePlanetPlayer(Bukkit.getPlayer(uuid), loadedPlanet, planetList);

            request.accept(planetPlayer);
            cacheHandler.getPlayerCache().put(uuid, planetPlayer);
        });
    }

    public void savePlayer(final UUID uuid) {
        databaseHandler.savePlayer(((BasePlanetPlayer) this.cacheHandler.getPlayerCache().get(uuid)).toDatabasePlayer());
    }


    public boolean checkIfPlanetIsLoadedByPlanetId(final UUID uuid) {
        return this.cacheHandler.getPlanetCache().containsKey(uuid);
    }

    public boolean checkIfPlanetIsLoadedByPlayerId(final Player player) {
        return checkIfPlanetIsLoadedByPlanetId(this.getPlanetIdByPlayerUUID(player.getUniqueId()));
    }


    @Override
    public void loadPlanetByPlanetId(final UUID planetId, final Consumer<LoadedPlanet> request) {
        final Planet planet = this.databaseHandler.getPlanet(planetId).toBasePlanet();
        this.preparePlanet(planet, loadedPlanet -> {
            cacheHandler.getPlanetCache().put(loadedPlanet.getUniqueID(), loadedPlanet);
            request.accept(loadedPlanet);
        });
    }

    /*public void loadPlanetByPlayerId(final UUID uuid, Consumer<LoadedPlanet> request) {
        if (this.cacheHandler.getPlayerCache().containsKey(uuid)) {
            this.loadPlanetByPlanetId(getPlanetIdByPlayerUUID(uuid), request);
        }
    }*/

    public void savePlanetByPlanetId(final UUID planetId) {
        this.dynamicNetworkAPI.saveSchematic(planetId, this.cacheHandler.getPlanetCache().get(planetId).getSchematic());
        this.databaseHandler.savePlanet(((BasePlanet) this.cacheHandler.getPlanetCache().get(planetId)).toDatabasePlanet());
    }

    public void savePlanetByPlayerId(final UUID uuid) {
        this.savePlanetByPlanetId(this.cacheHandler.getPlayerCache().get(uuid).getPlanet().getUniqueID());
    }

    private void preparePlanet(final Planet planet, final Consumer<LoadedPlanet> result) {
        this.dynamicNetworkAPI.getSchematic(planet.getUniqueID(), schematic -> {
            final Location location = gridHandler.getEmptyLocation();
            schematic.paste(FaweAPI.getWorld(location.getWorld().getName()), new Vector(location.getX(), location.getY(), location.getZ()));

            result.accept(new BaseLoadedPlanet(planet, location, gridHandler.getMaxSize()));
        });
    }

    private UUID getPlanetIdByPlayerUUID(final UUID uuid) {
        if (this.cacheHandler.getPlayerCache().containsKey(uuid)) {
            return this.cacheHandler.getPlayerCache().get(uuid).getPlanet().getUniqueID();
        }
        throw new NullPointerException(); //TODO: Custom exception
    }

    @Override
    public void autoLoadPlayer(final UUID uuid, final Consumer<PlanetPlayer> request) {
        this.loadPlayer(uuid, request);
    }

    @Override
    public void autoSavePlayer(final UUID uuid) {
        this.autoSavePlanetByPlayerId(uuid);
        this.savePlayer(uuid);
    }

    @Override
    public boolean isPlanetLoadedByPlanetId(final UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlanetLoadedByPlayerId(final UUID uuid) {
        return false;
    }

    @Override
    public LoadedPlanet getLoadedPlanetByPlanetId(final UUID uuid) {
        return this.cacheHandler.getPlanetCache().get(uuid);
    }

    @Override
    public LoadedPlanet getLoadedPlanetByPlayerId(final UUID uuid) {
        return this.getLoadedPlanetByPlanetId(this.getPlanetIdByPlayerUUID(uuid));
    }

    @Override
    public void autoLoadPlanetByPlayerId(final UUID uuid, final Consumer<LoadedPlanet> request) {

    }

    @Override
    public void autoSavePlanetByPlayerId(final UUID uuid) {
        this.savePlanetByPlayerId(uuid);
        this.cacheHandler.getPlanetCache().remove(this.cacheHandler.getPlayerCache().get(uuid).getPlanet().getUniqueID());
    }

    @Override
    public Planet getPlanetByPlayerId(final UUID uuid) {
        if (this.cacheHandler.getPlayerCache().containsKey(uuid)) {
            return this.getPlayer(uuid).getPlanet();
        } else {
            return this.databaseHandler.getPlanet(uuid).toBasePlanet();
        }
    }

    @Override
    public World getWorld() {
        return this.gridHandler.getWorld();
    }

    @Override
    public PlanetPlayer getPlayer(final UUID uuid) {
        return this.cacheHandler.getPlayerCache().get(uuid);
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return new HashSet<>(this.cacheHandler.getPlanetCache().values());
    }

    private class SaveTask extends BukkitRunnable {

        @Override
        public void run() {
            if (cacheHandler.getPlanetCache().size() != 0) {
                cacheHandler.getPlanetCache().forEach((uuid, planet) -> savePlanetByPlanetId(uuid));
            } else if (cacheHandler.getPlayerCache().size() != 0) {
                cacheHandler.getPlayerCache().forEach((uuid, planetPlayer) -> savePlayer(uuid));
            }
        }
    }
}
