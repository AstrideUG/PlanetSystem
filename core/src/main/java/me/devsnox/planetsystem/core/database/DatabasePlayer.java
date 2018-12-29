package me.devsnox.planetsystem.core.database;

import me.devsnox.planetsystem.api.planet.Planet;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;
import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
public class DatabasePlayer implements me.devsnox.planetsystem.api.database.DatabasePlayer {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;

    private UUID planetUniqueId;

    private List<UUID> memberedPlanets;

    public DatabasePlayer() {

    }

    public DatabasePlayer(final UUID uuid, final UUID planetUniqueId, final List<UUID> memberedPlanets) {
        this.uuid = uuid;
        this.planetUniqueId = planetUniqueId;
        this.memberedPlanets = memberedPlanets;
    }

    public static DatabasePlayer by(final Planet planet) {
        return new DatabasePlayer(planet.getUniqueID(), planet.getUniqueID(), planet.getMembers());
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public UUID getPlanetUniqueId() {
        return planetUniqueId;
    }

    @Override
    public List<UUID> getMemberedPlanets() {
        return memberedPlanets;
    }

}
