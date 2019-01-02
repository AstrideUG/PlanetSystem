package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class HomeCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        final Player target = player.getPlayer();
        target.setGameMode(GameMode.SURVIVAL);
        target.setFallDistance(0);
        target.teleport(player.getPlanet().getSpawnLocation().toBukkitLocation());
        player.getLogger().info("commands.home.teleport.success");
    }
}
