package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.planet.Planet;
import lombok.Getter;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
@Getter
public class DatabasePlayer implements de.astride.planetsystem.api.database.DatabasePlayer {
    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;
    private UUID planetUniqueId;

    public DatabasePlayer() {
    }

    public DatabasePlayer(final UUID uuid, final UUID planetUniqueId) {
        this.uuid = uuid;
        this.planetUniqueId = planetUniqueId;
    }

    //TODO: ACHTUNG!
    //  Das macht 0 Sinn #Bug
    public static DatabasePlayer by(final Planet planet) {
        return new DatabasePlayer(
                planet.getOwner(),
                planet.getUniqueID()
        );
    }
}
