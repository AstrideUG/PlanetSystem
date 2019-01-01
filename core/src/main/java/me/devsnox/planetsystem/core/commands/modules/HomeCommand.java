package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class HomeCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        player.getPlayer().teleport(player.getPlanet().getMiddle());
//        player.getPlanet().getSpawnLocation().toBukkitLocation(location -> ThreadUtils.sync(() -> {
//            final Player target = player.getPlayer();
//            target.setGameMode(GameMode.SURVIVAL);
//            target.setFallDistance(0);
//            target.teleport(location);
//            player.getLogger().info("Commands.Home.Teleport.Success");
//        }));
    }
}
