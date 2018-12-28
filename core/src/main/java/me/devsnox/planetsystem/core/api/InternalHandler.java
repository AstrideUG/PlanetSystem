package me.devsnox.planetsystem.core.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface InternalHandler {

    void loadPlayer(Player player);

    void savePlayer(UUID uuid);


    boolean isPlanetLoaded(UUID planetId);

    boolean isPlanetLoaded(Player player);

    void getLoadedPlanet(UUID planetId);

    void getLoadedPlanet(Player player);

    void loadPlanet(UUID planetId);

    void loadPlanet(Player player);

    void savePlanet(UUID planetId);

    void savePlanet(Player player);

}
