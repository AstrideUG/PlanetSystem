package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class HomeCommand implements PlanetCommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        Planet planet = player.getOwnedPlanet();
        player.getPlayer().teleport(planet.getSpawnLocation());
    }
}
