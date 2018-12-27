package me.devsnox.planetsystem.api.player;

import me.devsnox.planetsystem.api.planet.PlanetInfo;

public interface OfflinePlanetPlayer {

    boolean hasPlanet();

    PlanetInfo getPlanetInfo();

}
