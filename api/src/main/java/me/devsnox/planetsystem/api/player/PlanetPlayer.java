package me.devsnox.planetsystem.api.player;

import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.log.Logger;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlanetPlayer extends OfflinePlanetPlayer {

    @Override
    default UUID getUUID() {
        return getPlayer().getUniqueId();
    }

    Player getPlayer();

    @Override
    LoadedPlanet getPlanet();

    boolean isOnHisPlanet();

    boolean canBuild(Location location);

    default boolean canBuild(final Block block) {
        return canBuild(block.getLocation());
    }

    default boolean canNotBuild(final Location location) {
        return !canBuild(location);
    }

    default boolean canNotBuild(final Block block) {
        return !canBuild(block.getLocation());
    }

    PlanetLocation getLocation();

    Logger getLogger();

}
