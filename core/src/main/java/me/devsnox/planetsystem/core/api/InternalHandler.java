package me.devsnox.planetsystem.core.api;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public interface InternalHandler {

    void loadPlanetByPlanetId(UUID uuid, Consumer<LoadedPlanet> request);

    void autoLoadPlayer(UUID uuid, Consumer<PlanetPlayer> request);

    void autoSavePlayer(UUID uuid);

    boolean isPlanetLoadedByPlanetId(UUID uuid);

    boolean isPlanetLoadedByPlayerId(UUID uuid);

    LoadedPlanet getLoadedPlanetByPlanetId(UUID uuid);

    LoadedPlanet getLoadedPlanetByPlayerId(UUID uuid);

    void autoLoadPlanetByPlayerId(UUID uuid, Consumer<LoadedPlanet> request);

    void autoSavePlanetByPlayerId(UUID uuid);

    Planet getPlanetByPlayerId(UUID uuid);

    World getWorld();

    PlanetPlayer getPlayer(UUID uuid);

    Set<LoadedPlanet> getLoadedPlanets();
}
