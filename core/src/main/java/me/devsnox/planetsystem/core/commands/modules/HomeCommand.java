package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;
import me.devsnox.planetsystem.core.utils.ThreadUtils;

public class HomeCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        player.getPlanet().getSpawnLocation().toBukkitLocation(location -> ThreadUtils.sync(() -> {
            player.getPlayer().teleport(location);
            player.getLogger().log("Commands.Home.Teleport.Success");
        }));
    }
}
