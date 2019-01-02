package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class SethomeCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        if (player.getPlayer().getLocation().getWorld().equals(Holder.Impl.holder.getWorld())) {
            player.getPlanet().setSpawnLocation(PlanetLocation.create(player.getPlayer().getLocation(), player.getPlanet()));
            player.getLogger().info("commands.sethome.success");
        } else {
            player.getLogger().warn("commands.sethome.not_in_planet_world");
        }
    }
}
