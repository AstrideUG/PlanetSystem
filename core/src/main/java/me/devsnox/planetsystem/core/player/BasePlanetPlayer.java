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
import java.util.Objects;

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
        System.out.println(location);
        System.out.println(getPlanet());
        System.out.println(getPlanet().getInner());
        System.out.println(getPlanet().getInner().isInside(location));
        System.out.println(getPlanet().getOuter());
        System.out.println(getPlanet().getOuter().isInside(location));
//        System.out.println(Holder.Impl.holder.getPlanetData().getPlanet(location));
//        System.out.println(Holder.Impl.holder.getPlanetData().getPlanet(location).getMembers());
//        System.out.println(Holder.Impl.holder.getPlanetData().getPlanet(location).getMembers().contains(player.getUniqueId()));
//
        if (this.getPlanet().getInner().isInside(location)) return true;
        else {
            final Planet planet = Holder.Impl.holder.getPlanetData().getPlanet(location);
            if (planet == null || planet.getMembers() == null) return false;
            return planet.getMembers().contains(player.getUniqueId());
        }
    }

    @Override
    public PlanetLocation getLocation() {
        return PlanetLocation.create(player.getLocation(), getPlanet());
    }

    @Override
    public List<Planet> getMemberedPlanets() {
        return this.memberedPlanets;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final BasePlanetPlayer that = (BasePlanetPlayer) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(memberedPlanets, that.memberedPlanets) &&
                Objects.equals(logger, that.logger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, memberedPlanets, logger);
    }

    @Override
    public String toString() {
        return "BasePlanetPlayer{" +
                "player=" + player +
                ", memberedPlanets=" + memberedPlanets +
                ", logger=" + logger +
                '}';
    }
}
