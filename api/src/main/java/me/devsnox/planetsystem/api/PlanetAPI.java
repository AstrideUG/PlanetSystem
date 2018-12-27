package me.devsnox.planetsystem.api;

import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlanetAPI {

    /**
     * Can only be used when the player is online
     * @param uuid
     * @return
     */
    PlanetPlayer getPlayer(UUID uuid);
    PlanetPlayer getPlayer(Player player);

    boolean isInPlanetWorld(Player player);

    PlanetLocation createLocation(UUID planetID, double x, double y, double z, float yaw, float pitch);
}
