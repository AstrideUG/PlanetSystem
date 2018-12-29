package me.devsnox.planetsystem.core.player;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.log.KeyLogger;
import me.devsnox.planetsystem.api.log.Logger;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.log.BasePlayerKeyLogger;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class BasePlanetPlayer extends BaseOfflinePlanetPlayer implements PlanetPlayer {

    private final Player player;
    private final List<Planet> memberedPlanets;
    private final KeyLogger logger;

    public BasePlanetPlayer(final Player player, final LoadedPlanet loadedPlanet, final List<Planet> memberedPlanets) {
        super(player.getUniqueId(), loadedPlanet, memberedPlanets);
        this.player = player;
        this.memberedPlanets = memberedPlanets;
        this.logger = new BasePlayerKeyLogger(player, new HashMap<>());
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public LoadedPlanet getPlanet() {
        return (LoadedPlanet) super.getPlanet();
    }

    @Override
    public boolean isOnHisPlanet() {
        return getPlanet().getInner().isInside(player);
    }

    @Override
    public boolean canBuild(final Location location) {
        if (this.getPlanet().getInner().isInside(player)) return true;
        else return Holder.Impl.holder.getPlanetData().getPlanet(location).getMembers().contains(player.getUniqueId());
    }

    @Override
    public PlanetLocation getLocation() {
        return PlanetLocation.createFromBukkitLocation(getPlanet(), player.getLocation());
    }

    @Override
    public List<Planet> getMemberedPlanets() {
        return this.memberedPlanets;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
