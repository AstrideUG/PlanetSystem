package me.devsnox.planetsystem.core.player;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.OfflinePlanetPlayer;

import java.util.Objects;
import java.util.UUID;

public class BaseOfflinePlanetPlayer implements OfflinePlanetPlayer {

    private final UUID uuid;
    private final Planet planet;

    public BaseOfflinePlanetPlayer(final UUID uuid, final Planet planet) {
        this.uuid = uuid;
        this.planet = planet;
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BaseOfflinePlanetPlayer that = (BaseOfflinePlanetPlayer) o;
        return uuid.equals(that.uuid) &&
                planet.equals(that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, planet);
    }

    @Override
    public String toString() {
        return "BaseOfflinePlanetPlayer{" +
                "uuid=" + uuid +
                ", planet=" + planet +
                '}';
    }
}
