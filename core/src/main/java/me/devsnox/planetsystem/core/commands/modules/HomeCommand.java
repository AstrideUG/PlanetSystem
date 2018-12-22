package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.Planet;
import me.devsnox.planetsystem.api.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.CommandModule;

public class HomeCommand implements CommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        Planet planet = player.getOwnedPlanet();

        player.getPlayer().teleport(planet.getSpawnLocation());
    }
}
