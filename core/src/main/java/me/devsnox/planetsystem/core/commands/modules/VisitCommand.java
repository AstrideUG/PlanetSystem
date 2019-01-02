package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.log.Logger;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;

public class VisitCommand implements PlanetCommandModule {

    @Override
    public void execute(final PlanetPlayer player, final String[] args) {
        if (args.length != 0) player.getMemberedPlanets().forEach(planet -> {
            if (planet.getName().equals(args[0])) {
                //TODO: Add content!
            }
        });
        else player.getLogger().log(Logger.Level.WARNING, "Commands.Visit.Failed.ArgsSizeIs0");
    }
}
