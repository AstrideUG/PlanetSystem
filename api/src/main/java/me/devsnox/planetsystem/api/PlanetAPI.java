package me.devsnox.planetsystem.api;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;

import java.util.UUID;

public interface PlanetAPI {

    Planet getPlanet(UUID uuid);

    /**
     * Can only be used when the player is online
     * @param uuid
     * @return
     */
    PlanetPlayer getPlayer(UUID uuid);
}
