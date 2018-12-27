package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class HomeCommand implements PlanetCommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        if(player.hasPlanet()) {
            player.getPlayer().teleport(player.getPlanet().getSpawnLocation());
            player.getLogger().log(""); //TODO: Add message-key
        } else {
            player.getLogger().log(""); //TODO: Add message-key
        }
    }
}
