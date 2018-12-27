package me.devsnox.planetsystem.api.player;

import me.devsnox.planetsystem.api.planet.Planet;

import java.util.List;
import java.util.UUID;

public interface OfflinePlanetPlayer {

    UUID getUUID();

    Planet getPlanet();

    List<Planet> getMemberedPlanets();
}
