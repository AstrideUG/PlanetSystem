package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.atmosphere.Atmosphere;
import de.astride.planetsystem.api.database.DatabasePlanet;
import de.astride.planetsystem.api.inline.Owner;
import de.astride.planetsystem.api.location.PlanetLocation;
import de.astride.planetsystem.api.planet.Planet;
import de.astride.planetsystem.core.database.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Indexed;
import xyz.morphia.annotations.Property;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value = "planets", noClassnameStored = true)
public class BasicDatabasePlanet extends DatabaseEntity implements DatabasePlanet {

    @Indexed
    private String name;
    @Indexed
    @Property("owner")
    private UUID ownerUniqueId;
    private Set<Owner> members;
    private Atmosphere atmosphere;
    private PlanetLocation planetLocation;
    private Map<String, Object> metaData;

    public BasicDatabasePlanet(
            UUID uuid,
            String name,
            UUID ownerUniqueId,
            Set<Owner> members,
            Atmosphere atmosphere,
            PlanetLocation planetLocation,
            Map<String, Object> metaData
    ) {
        super(uuid);
        this.name = name;
        this.ownerUniqueId = ownerUniqueId;
        this.members = members;
        this.atmosphere = atmosphere;
        this.planetLocation = planetLocation;
        this.metaData = metaData;
    }

    public static BasicDatabasePlanet by(final Planet planet) {
        return new BasicDatabasePlanet(
                planet.getUniqueID(),
                planet.getName(),
                planet.getOwner(),
                planet.getMembers(),
                planet.getAtmosphere(),
                planet.getSpawnLocation(),
                planet.getMetaData()
        );
    }

    @Override
    @NotNull
    public Set<Owner> getMembers() {
        return this.members != null ? this.members : new HashSet<>();
    }

    @NotNull
    @Override
    public Atmosphere getAtmosphere() {
        return null;
    }

}
