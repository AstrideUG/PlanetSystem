package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.database.DatabasePlayer;
import de.astride.planetsystem.api.planet.Planet;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.IndexOptions;
import xyz.morphia.annotations.Indexed;

import java.util.UUID;

@NoArgsConstructor
@Data
@Entity(value = "players", noClassnameStored = true)
public class BasicDatabasePlayer implements DatabasePlayer {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;
    @Indexed(options = @IndexOptions(unique = true))
    private UUID planetUniqueId;

    public BasicDatabasePlayer(final UUID uuid, final UUID planetUniqueId) {
        this.uuid = uuid;
        this.planetUniqueId = planetUniqueId;
    }

    public static BasicDatabasePlayer by(final Planet planet) {
        return new BasicDatabasePlayer(
                planet.getOwner(),
                planet.getUniqueID()
        );
    }
}
