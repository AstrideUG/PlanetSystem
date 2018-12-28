package me.devsnox.planetsystem.api;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface PlanetAPI {

    Planet getPlanet(UUID uuid);


    Planet getPlanet(Location location);

    /**
     * Can only be used when the player is online
     *
     * @param uuid
     * @return
     */
    PlanetPlayer getPlayer(UUID uuid);

    default PlanetPlayer getPlayer(final Player player) {
        return getPlayer(player.getUniqueId());
    }

    World getWorld();

    Set<LoadedPlanet> getLoadedPlanets();
}
