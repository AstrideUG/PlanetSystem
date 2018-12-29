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

    @Deprecated
    void loadPlanetByPlanetId(UUID uuid, Consumer<LoadedPlanet> request);

    @Deprecated
    void autoLoadPlayer(UUID uuid, Consumer<PlanetPlayer> request);

    @Deprecated
    void autoSavePlayer(UUID uuid);

    @Deprecated
    boolean isPlanetLoadedByPlanetId(UUID uuid);

    @Deprecated
    boolean isPlanetLoadedByPlayerId(UUID uuid);

    @Deprecated
    LoadedPlanet getLoadedPlanetByPlanetId(UUID uuid);

    @Deprecated
    LoadedPlanet getLoadedPlanetByPlayerId(UUID uuid);

    @Deprecated
    void autoLoadPlanetByPlayerId(UUID uuid, Consumer<LoadedPlanet> request);

    @Deprecated
    void autoSavePlanetByPlayerId(UUID uuid);

    @Deprecated
    Planet getPlanetByPlayerId(UUID uuid);

    @Deprecated
    World getWorld();

    @Deprecated
    PlanetPlayer getPlayer(UUID uuid);

    @Deprecated
    Set<LoadedPlanet> getLoadedPlanets();
}
