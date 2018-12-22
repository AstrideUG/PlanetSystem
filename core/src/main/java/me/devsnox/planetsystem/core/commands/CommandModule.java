package me.devsnox.planetsystem.core.commands;

import me.devsnox.planetsystem.api.PlanetPlayer;

public interface CommandModule {

    void execute(PlanetPlayer player, String args[]);
}
