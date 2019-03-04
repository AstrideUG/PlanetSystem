package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.planet.Planet;
import lombok.Data;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.IndexOptions;
import xyz.morphia.annotations.Indexed;

import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
@Data
public class DatabasePlayer implements de.astride.planetsystem.api.database.DatabasePlayer {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;
    @Indexed(options = @IndexOptions(unique = true))
    private UUID planetUniqueId;

    public DatabasePlayer() {
    }

    public DatabasePlayer(final UUID uuid, final UUID planetUniqueId) {
        this.uuid = uuid;
        this.planetUniqueId = planetUniqueId;
    }

    public static DatabasePlayer by(final Planet planet) {
        return new DatabasePlayer(
                planet.getOwner(),
                planet.getUniqueID()
        );
    }
}
