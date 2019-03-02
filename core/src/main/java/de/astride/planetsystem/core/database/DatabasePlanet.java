package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.inline.Owner;
import de.astride.planetsystem.api.location.PlanetLocation;
import de.astride.planetsystem.api.planet.Planet;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(value = "planets", noClassnameStored = true)
@Data
@NoArgsConstructor
public class DatabasePlanet implements de.astride.planetsystem.api.database.DatabasePlanet {
    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;
    @Indexed
    private String name;
    @Indexed
    @Property("owner")
    private UUID ownerUniqueId;
    private List<UUID> members;
    private byte size;
    private PlanetLocation planetLocation;

    public DatabasePlanet(final UUID uuid, final String name, final UUID ownerUniqueId, final List<UUID> members, final byte size, final PlanetLocation planetLocation) {
        this.uuid = uuid;
        this.name = name;
        this.ownerUniqueId = ownerUniqueId;
        this.members = members;
        this.size = size;
        this.planetLocation = planetLocation;
    }

    public static DatabasePlanet by(final Planet planet) {
        return new DatabasePlanet(
                planet.getUniqueID(),
                planet.getName(),
                planet.getOwner(),
                planet.getMembers().stream().map(Owner::getUuid).collect(Collectors.toList()),
                planet.getAtmosphere().getSize(),
                planet.getSpawnLocation()
        );
    }

    @NotNull
    @Override
    public List<UUID> getMembers() {
        return this.members == null ? new ArrayList<>() : this.members;
    }


}