package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class ListCommand implements PlanetCommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(player.getOwnedPlanet().getMembers().toArray());

        player.getPlayer().sendMessage(""); //TODO:
    }
}
