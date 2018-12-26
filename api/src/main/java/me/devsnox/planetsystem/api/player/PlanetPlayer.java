package me.devsnox.planetsystem.api.player;

import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlanetPlayer {

    Player getPlayer();

    Planet getOwnedPlanet();
    List<Planet> getMemberedPlanets();
}
