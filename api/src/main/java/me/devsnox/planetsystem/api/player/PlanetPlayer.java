package me.devsnox.planetsystem.api.player;

import com.sun.istack.internal.Nullable;
import me.devsnox.planetsystem.api.PlanetFactory;
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

    boolean canBuild(Location location);

    default boolean canBuild(final Block block) {
        return canBuild(block.getLocation());
    }

    @Nullable
    default PlanetLocation getLocation() {
        return PlanetLocation.createFromBukkitLocation(PlanetFactory.planetAPI.getPlanet(getPlayer().getLocation()), this.getPlayer().getLocation());
    }

    Logger getLogger();

}
