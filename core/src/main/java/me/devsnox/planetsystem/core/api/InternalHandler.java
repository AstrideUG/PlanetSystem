package me.devsnox.planetsystem.core.api;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface InternalHandler {

    void autoLoadPlayer(UUID uuid);

    void autoSavePlayer(UUID uuid);

    boolean isPlanetLoadedByPlanetId(UUID uuid);

    boolean isPlanetLoadedByPlayerId(UUID uuid);

    LoadedPlanet getLoadedPlanetByPlanetId(UUID uuid);

    LoadedPlanet getLoadedPlanetByPlayerId(UUID uuid);

    void autoLoadPlanetByPlayerId(UUID uuid);

    void autoSavePlanetByPlayerId(UUID uuid);
}
