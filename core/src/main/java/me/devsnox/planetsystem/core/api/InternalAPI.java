package me.devsnox.planetsystem.core.api;

import me.devsnox.planetsystem.api.PlanetAPI;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;

import java.util.UUID;

public interface InternalAPI {

    PlanetAPI getPlanetAPI();

    InternalPlanet getInternalPlanet(UUID uuid);

    InternalPlanet getInternalPlanet(Planet planet);

    InternalPlayer getInternalPlayer(UUID uuid);

    InternalPlayer getInternalPlayer(PlanetPlayer planetPlayer);


    boolean isPlanetLoaded(Integer id);

    void loadPlanet(UUID uuid);

    void savePlanet(UUID uuid);


    boolean isPlayerLoaded(UUID uuid);

    void loadPlayer(UUID uuid);

    void savePlayer(UUID uuid);
}
