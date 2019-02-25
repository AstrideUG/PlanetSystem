package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.inline.Owner;
import de.astride.planetsystem.api.planet.Planet;
import lombok.Getter;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(value = "players", noClassnameStored = true)
@Getter
public class DatabasePlayer implements de.astride.planetsystem.api.database.DatabasePlayer {
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

    //TODO: ACHTUNG!
    //  Das macht 0 Sinn #Bug
    public static DatabasePlayer by(final Planet planet) {
        return new DatabasePlayer(
                planet.getOwner(),
                planet.getUniqueID(),
                planet.getMembers().stream().map(Owner::getUuid).collect(Collectors.toList())
        );
    }
}
