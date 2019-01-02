package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class InfoCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        if (player.getPlanet().getInner().isInside(player.getLocation())) {
            player.getLogger().success("commands.info.success");
        } else {
            player.getLogger().warn("commands.info.failed.not_own_planet");
        }
    }
}
