package me.devsnox.planetsystem.core;

import me.devsnox.planetsystem.api.PlanetAPI;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlanetManager implements PlanetAPI {

    @Override
    public PlanetPlayer getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public PlanetPlayer getPlayer(Player player) {
        return null;
    }

    @Override
    public boolean isInPlanetWorld(Player player) {
        return false;
    }
}
