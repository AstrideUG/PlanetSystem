package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class InfoCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        if (player.getPlanet().isInside(player.getPlayer())) {
            //TODO: Add message handling
        } else {
            player.getLogger().log(""); //TODO: Add message-key
        }
    }
}
