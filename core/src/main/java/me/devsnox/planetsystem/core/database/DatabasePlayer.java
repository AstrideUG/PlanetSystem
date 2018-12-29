package me.devsnox.planetsystem.core.database;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity(value = "players", noClassnameStored = true)
public class DatabasePlayer implements me.devsnox.planetsystem.api.database.DatabasePlayer {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private final UUID uuid;
    private final UUID planetUniqueId;
    private final List<UUID> memberedPlanets;

    public DatabasePlayer(final UUID uuid, final UUID planetUniqueId, final List<UUID> memberedPlanets) {
        this.uuid = uuid;
        this.planetUniqueId = planetUniqueId;
        this.memberedPlanets = memberedPlanets;
    }

    public static DatabasePlayer by(Planet planet) {
        return new DatabasePlayer(planet.getUniqueID(), planet.getUniqueID(), planet.getMembers());
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getPlanetUniqueId() {
        return planetUniqueId;
    }

    public List<UUID> getMemberedPlanets() {
        return memberedPlanets;
    }

}
