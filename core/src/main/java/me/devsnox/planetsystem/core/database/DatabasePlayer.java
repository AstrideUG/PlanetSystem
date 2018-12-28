package me.devsnox.planetsystem.core.database;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;
import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
public class DatabasePlayer {

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