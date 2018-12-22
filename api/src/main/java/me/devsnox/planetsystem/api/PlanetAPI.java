package me.devsnox.planetsystem.api;

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
