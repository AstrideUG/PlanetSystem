package me.devsnox.planetsystem.core.player;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.OfflinePlanetPlayer;
import me.devsnox.planetsystem.core.database.DatabasePlayer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BaseOfflinePlanetPlayer implements OfflinePlanetPlayer {

    private final UUID uuid;
    private final Planet planet;
    private List<Planet> memberedPlanets;

    public BaseOfflinePlanetPlayer(final UUID uuid, final Planet planet, List<Planet> memberedPlanets) { //TODO: Add lombok null check
        this.uuid = uuid;
        this.planet = planet;
        this.memberedPlanets = memberedPlanets;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Planet getPlanet() {
        return planet;
    }

    @Override
    public List<Planet> getMemberedPlanets() {
        return this.memberedPlanets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseOfflinePlanetPlayer that = (BaseOfflinePlanetPlayer) o;
        return uuid.equals(that.uuid) &&
                planet.equals(that.planet) &&
                memberedPlanets.equals(that.memberedPlanets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, planet, memberedPlanets);
    }

    @Override
    public String toString() {
        return "BaseOfflinePlanetPlayer{" +
                "uuid=" + uuid +
                ", planet=" + planet +
                ", memberedPlanets=" + memberedPlanets +
                '}';
    }
}
