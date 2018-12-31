package me.devsnox.planetsystem.core.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.log.KeyLogger;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.log.BasePlayerKeyLogger;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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
    public LoadedPlanet getPlanet() {
        return (LoadedPlanet) super.getPlanet();
    }

    @Override
    public boolean isOnHisPlanet() {
        return getPlanet().getInner().isInside(getLocation());
    }

    @Override
    public boolean canBuild(final Location location) {
        System.out.println("Start canBuild(Location)");
        System.out.println(location.toVector());
        final PlanetLocation planetLocation = PlanetLocation.create(location, getPlanet());
//        System.out.println(planetLocation);
        System.out.println(planetLocation.getVector());
//        System.out.println(getPlanet());
        System.out.println(getPlanet().getInner().getMin());
        System.out.println(getPlanet().getInner().getMax());
        System.out.println(isOnHisPlanet());

        if (this.getPlanet().getInner().isInside(planetLocation)) {
            System.out.println("Ends canBuild(Location)");
            return true;
        }
        final Planet planet = Holder.Impl.holder.getPlanetData().getPlanet(location);
        System.out.println("Ends canBuild(Location)");
        if (planet == null || planet.getMembers() == null) return false;
        return planet.getMembers().contains(player.getUniqueId());
    }

    @Override
    public PlanetLocation getLocation() {
        return PlanetLocation.create(player.getLocation(), getPlanet());
    }

}
