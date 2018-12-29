package me.devsnox.planetsystem.core.api;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.api.data.PlanetData;
import me.devsnox.planetsystem.core.api.data.PlayerData;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface Holder {

    World getWorld();

    Set<LoadedPlanet> getLoadedPlanets();

    PlayerData getPlayerData();

    PlanetData getPlanetData();

}
