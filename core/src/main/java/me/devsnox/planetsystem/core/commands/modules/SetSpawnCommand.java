package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class SetSpawnCommand implements PlanetCommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        if (player.hasPlanet()) {
            player.getPlanet().setSpawnLocation(player.getPlayer().getLocation());
            player.getLogger().log("");
        } else {
            player.getLogger().log("");
        }
    }
}
