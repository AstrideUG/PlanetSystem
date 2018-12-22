package me.devsnox.planetsystem.api;

import org.bukkit.entity.Player;

import java.util.List;

public interface PlanetPlayer {

    Player getPlayer();

    Planet getOwnedPlanet();
    List<Planet> getMemberedPlanets();
}
