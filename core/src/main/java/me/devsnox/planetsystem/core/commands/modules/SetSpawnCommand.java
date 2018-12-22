package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.PlanetPlayer;
import me.devsnox.planetsystem.core.api.InternalFactory;
import me.devsnox.planetsystem.core.api.InternalPlanet;
import me.devsnox.planetsystem.core.commands.CommandModule;

public class SetSpawnCommand implements CommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        //TODO: Check if player is on his own island is and if player do have a planet
        InternalPlanet internalPlanet = InternalFactory.internalAPI.getInternalPlanet(player.getOwnedPlanet());
        internalPlanet.setSpawnLocation(player.getPlayer().getLocation());
    }
}
