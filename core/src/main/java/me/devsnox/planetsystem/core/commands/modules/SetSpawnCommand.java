package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class SetSpawnCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        player.getPlanet().setSpawnLocation(player.getPlayer().getLocation());
        player.getLogger().log("");
    }
}
