package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.database.DatabasePlanet;
import de.astride.planetsystem.api.inline.Owner;
import de.astride.planetsystem.api.location.PlanetLocation;
import de.astride.planetsystem.api.planet.Planet;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Entity(value = "planets", noClassnameStored = true)
public class BasicDatabasePlanet implements DatabasePlanet {
    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;
    @Indexed
    private String name;
    @Indexed
    @Property("owner")
    private UUID ownerUniqueId;
    private Set<UUID> members;
    private byte size;
    private PlanetLocation planetLocation;
    private Map<String, Object> metaData;

    public BasicDatabasePlanet(final UUID uuid, final String name, final UUID ownerUniqueId, final Set<UUID> members, final byte size, final PlanetLocation planetLocation) {
        this.uuid = uuid;
        this.name = name;
        this.ownerUniqueId = ownerUniqueId;
        this.members = members;
        this.size = size;
        this.planetLocation = planetLocation;
    }

    public static BasicDatabasePlanet by(final Planet planet) {
        return new BasicDatabasePlanet(
                planet.getUniqueID(),
                planet.getName(),
                planet.getOwner(),
                planet.getMembers().stream().map(Owner::getUuid).collect(Collectors.toSet()),
                planet.getAtmosphere().getSize(),
                planet.getSpawnLocation()
        );
    }

    @Override
    @NotNull
    public Set<UUID> getMembers() {
        return this.members != null ? this.members : new HashSet<>();
    }
}
