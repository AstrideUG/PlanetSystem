package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class SetSpawnCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        player.getPlanet().setSpawnLocation(PlanetLocation.create(player.getPlayer().getLocation(), player.getPlanet()));
        player.getLogger().info("Commands.SetSpawn.Success");
    }
}
