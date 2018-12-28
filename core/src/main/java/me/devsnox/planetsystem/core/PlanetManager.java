package me.devsnox.planetsystem.core;

import me.devsnox.planetsystem.api.PlanetAPI;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.api.InternalHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class PlanetManager implements PlanetAPI {

    private InternalHandler internalHandler;

    public PlanetManager(InternalHandler internalHandler) {
        this.internalHandler = internalHandler;
    }

    @Override
    public Planet getPlanet(UUID uuid) {
        return InternalFactory.internalHandler.getPlanetByPlayerId(uuid);
    }

    @Override
    public Planet getPlanet(Location location) {
        for (LoadedPlanet loadedPlanet : this.internalHandler.getLoadedPlanets()) {
            if (loadedPlanet.getInner().isInside(location)) {
                return loadedPlanet;
            }
        }

        return null;
    }

    @Override
    public PlanetPlayer getPlayer(UUID uuid) {
        return internalHandler.getPlayer(uuid);
    }

    @Override
    public World getWorld() {
        return internalHandler.getWorld();
    }

    @Override
    public Set<LoadedPlanet> getLoadedPlanets() {
        return this.internalHandler.getLoadedPlanets();
    }
}
