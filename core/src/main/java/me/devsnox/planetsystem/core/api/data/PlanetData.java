package me.devsnox.planetsystem.core.api.data;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;

import java.util.UUID;
import java.util.function.Consumer;

public interface PlanetData {

    void load(UUID owner, Consumer<LoadedPlanet> request);

    void save(UUID owner);

    boolean isLoaded(UUID uuid);

    LoadedPlanet getLoadedPlanet(UUID uuid);

}
