package me.devsnox.planetsystem.core.commands;

import me.devsnox.planetsystem.api.player.PlanetPlayer;

public interface PlanetCommandModule {

    void execute(PlanetPlayer player, String[] args);

}
