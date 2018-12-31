package me.devsnox.planetsystem.core.player;

import lombok.Data;
import lombok.NonNull;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.OfflinePlanetPlayer;
import me.devsnox.planetsystem.core.database.DatabasePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class BaseOfflinePlanetPlayer implements OfflinePlanetPlayer {

    private final @NonNull UUID uuid;
    private final @NonNull Planet planet;
    private final @NonNull List<Planet> memberedPlanets;

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public DatabasePlayer toDatabasePlayer() {
        final List<UUID> planetIds = new ArrayList<>();

        this.memberedPlanets.forEach(planet -> planetIds.add(planet.getUniqueID()));

        return new DatabasePlayer(this.uuid, this.planet.getUniqueID(), planetIds);
    }
}
