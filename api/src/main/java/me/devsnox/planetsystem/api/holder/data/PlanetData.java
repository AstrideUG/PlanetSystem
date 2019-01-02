package me.devsnox.planetsystem.api.holder.data;

import me.devsnox.planetsystem.api.location.PlanetLocation;
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

    void unload(UUID owner);

    default boolean isLoaded(final UUID owner) {
        return this.getLoadedPlanetByOwner(owner) != null;
    }

    @Nullable
    default LoadedPlanet getLoadedPlanetByOwner(final UUID owner) {
        for (final LoadedPlanet planet : this.getLoadedPlanets())
            if (planet.getOwnerUniqueID().equals(owner)) return planet;
        return null;
    }

    @Nullable
    default LoadedPlanet getLoadedPlanet(final UUID uuid) {
        for (final LoadedPlanet planet : this.getLoadedPlanets())
            if (planet.getUniqueID().equals(uuid)) return planet;
        return null;
    }

    @Nullable
    default Planet getPlanet(final Location location) {
        for (final LoadedPlanet loadedPlanet : this.getLoadedPlanets())
            if (loadedPlanet.getInner().isInside(PlanetLocation.create(location, loadedPlanet)))
                return loadedPlanet;
        return null;
    }

    Set<LoadedPlanet> getLoadedPlanets();

}
