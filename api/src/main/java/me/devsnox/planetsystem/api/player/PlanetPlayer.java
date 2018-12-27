package me.devsnox.planetsystem.api.player;

import me.devsnox.planetsystem.api.log.Logger;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.planet.PlanetInfo;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlanetPlayer extends OfflinePlanetPlayer {

    Player getPlayer();

    boolean hasPlanet();

    Planet getPlanet();


    boolean canBuild(Location location);

    default boolean canBuild(Block block) {
        return canBuild(block);
    }

    List<PlanetInfo> getMemberedPlanets();

    Logger getLogger();

}
