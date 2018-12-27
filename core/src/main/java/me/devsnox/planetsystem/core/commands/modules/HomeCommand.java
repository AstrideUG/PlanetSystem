package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class HomeCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        player.getPlayer().teleport(player.getPlanet().getSpawnLocation().toBukkitLocation());
        player.getLogger().log(""); //TODO: Add message-key
    }
}
