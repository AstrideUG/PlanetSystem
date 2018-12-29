package me.devsnox.planetsystem.api.holder.data;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public interface PlanetData {

    void load(UUID owner, Consumer<LoadedPlanet> request);

    void save(UUID owner);

    default boolean isLoaded(UUID uuid) {
        return getLoadedPlanet(uuid) != null;
    }

    @Nullable
    default LoadedPlanet getLoadedPlanet(UUID uuid) {
        for (LoadedPlanet planet : getLoadedPlanets()) if (planet.getUniqueID() == uuid) return planet;
        return null;
    }

    @Nullable
    default Planet getPlanet(Location location) {
        for (LoadedPlanet loadedPlanet : getLoadedPlanets())
            if (loadedPlanet.getInner().isInside(location)) return loadedPlanet;
        return null;
    }

    Set<LoadedPlanet> getLoadedPlanets();

}
