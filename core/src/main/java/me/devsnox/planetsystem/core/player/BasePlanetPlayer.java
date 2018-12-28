package me.devsnox.planetsystem.core.player;

import me.devsnox.planetsystem.api.PlanetFactory;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.log.Logger;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class BasePlanetPlayer extends BaseOfflinePlanetPlayer implements PlanetPlayer {

    private final Player player;
    private final List<Planet> memberedPlanets;

    public BasePlanetPlayer(final Player player, final LoadedPlanet loadedPlanet, final List<Planet> memberedPlanets) {
        super(player.getUniqueId(), loadedPlanet, memberedPlanets);
        this.player = player;
        this.memberedPlanets = memberedPlanets;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public LoadedPlanet getPlanet() {
        return (LoadedPlanet) super.getPlanet();
    }

    @Override
    public boolean canBuild(final Location location) {
        if (this.getPlanet().getInner().isInside(player)) return true;
        else return PlanetFactory.planetAPI.getPlanet(location).getMembers().contains(player.getUniqueId());
    }

    @Override
    public PlanetLocation getLocation() {
        return null; //TODO:
    }

    @Override
    public List<Planet> getMemberedPlanets() {
        return this.memberedPlanets;
    }

    @Override
    public Logger getLogger() {
        return null; //TODO: Add Logger handling
    }
}
