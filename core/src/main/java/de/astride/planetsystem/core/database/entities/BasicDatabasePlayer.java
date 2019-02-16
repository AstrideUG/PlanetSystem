package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.database.DatabasePlayer;
import de.astride.planetsystem.api.planet.Planet;
import de.astride.planetsystem.core.database.DatabaseEntity;
import lombok.Getter;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;
import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
@Getter
public class BasicDatabasePlayer extends DatabaseEntity implements DatabasePlayer {

    private UUID planetUniqueId;
    private List<UUID> memberedPlanets;

    public BasicDatabasePlayer() {
    }

    public BasicDatabasePlayer(final UUID uuid, final UUID planetUniqueId, final List<UUID> memberedPlanets) {
        super(uuid);
        this.planetUniqueId = planetUniqueId;
        this.memberedPlanets = memberedPlanets;
    }

    public static BasicDatabasePlayer by(final Planet planet) {
        return new BasicDatabasePlayer(planet.getOwnerUniqueID(), planet.getUniqueID(), planet.getMembers());
    }
}
