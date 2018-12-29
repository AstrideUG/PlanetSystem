package me.devsnox.planetsystem.core.holder;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlanetData;
import me.devsnox.planetsystem.api.holder.data.PlayerData;
import me.devsnox.planetsystem.core.database.DatabaseHandler;
import me.devsnox.planetsystem.core.holder.data.PlanetDataImpl;
import me.devsnox.planetsystem.core.holder.data.PlayerDataImpl;
import me.devsnox.planetsystem.core.world.GridHandler;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HolderImpl implements Holder {

    private final Set<LoadedPlanet> loadedPlanets;
    private final PlayerData playerData;
    private final PlanetData planetData;

    private final DatabaseHandler databaseHandler;
    private final GridHandler gridHandler;

    public HolderImpl(JavaPlugin javaPlugin) {
        this.loadedPlanets = new HashSet<>();
        this.playerData = new PlayerDataImpl(this);
        this.planetData = new PlanetDataImpl(this);

        this.databaseHandler = new DatabaseHandler();
        this.gridHandler = new GridHandler("PlanetWorld", 2048); //TODO: Add config handling

        new BukkitRunnable() {
            @Override
            public void run() {
                Set<LoadedPlanet> planets = getPlanetData().getLoadedPlanets();
                Set<PlanetPlayer> players = getPlayerData().getPlayers();

                if (planets.size() != 0) for (LoadedPlanet planet : planets) getPlanetData().save(planet.getUniqueID());
                if (players.size() != 0) for (PlanetPlayer player : players) getPlayerData().save(player.getUUID());
            }
        }.runTaskTimer(javaPlugin, 0, 20 * 60);
    }

    @Override
    public World getWorld() {
        return gridHandler.getWorld();
    }

    @Override
    public PlayerData getPlayerData() {
        return playerData;
    }

    @Override
    public PlanetData getPlanetData() {
        return planetData;
    }

    @Override
    public me.devsnox.planetsystem.api.handler.DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    @Override
    public GridHandler getGridHandler() {
        return gridHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HolderImpl holder = (HolderImpl) o;
        return Objects.equals(loadedPlanets, holder.loadedPlanets) &&
                Objects.equals(playerData, holder.playerData) &&
                Objects.equals(planetData, holder.planetData) &&
                Objects.equals(databaseHandler, holder.databaseHandler) &&
                Objects.equals(gridHandler, holder.gridHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loadedPlanets, playerData, planetData, databaseHandler, gridHandler);
    }

    @Override
    public String toString() {
        return "HolderImpl{" +
                ", loadedPlanets=" + loadedPlanets +
                ", playerData=" + playerData +
                ", planetData=" + planetData +
                ", databaseHandler=" + databaseHandler +
                ", gridHandler=" + gridHandler +
                '}';
    }
}
