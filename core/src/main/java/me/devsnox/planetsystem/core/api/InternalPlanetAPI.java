package me.devsnox.planetsystem.core.api;

import java.util.UUID;

public interface InternalPlanetAPI {


    boolean isPlanetLoaded(Integer id);

    void loadPlanet(UUID uuid);

    void savePlanet(UUID uuid);


    boolean isPlayerLoaded(UUID uuid);

    void loadPlayer(UUID uuid);

    void savePlayer(UUID uuid);
}
