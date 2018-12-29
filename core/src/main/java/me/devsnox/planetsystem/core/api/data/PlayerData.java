package me.devsnox.planetsystem.core.api.data;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public interface PlayerData {

    void load(UUID uuid, Consumer<PlanetPlayer> request);

    void save(UUID uuid);

    @Nullable
    PlanetPlayer getPlayer(UUID uuid);

    boolean isPlanetLoaded(UUID owner);

    Planet getPlanet(UUID owner);

    LoadedPlanet getLoadedPlanet(UUID owner);

}
