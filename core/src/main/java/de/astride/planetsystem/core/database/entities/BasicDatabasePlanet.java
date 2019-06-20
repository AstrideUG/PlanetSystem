/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.atmosphere.Atmosphere;
import de.astride.planetsystem.api.database.DatabasePlanet;
import de.astride.planetsystem.api.inline.Owner;
import de.astride.planetsystem.api.location.PlanetLocation;
import de.astride.planetsystem.core.database.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.Embedded;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Indexed;
import xyz.morphia.annotations.Property;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value = "planets", noClassnameStored = true)
public class BasicDatabasePlanet extends DatabaseEntity implements DatabasePlanet {

    @Indexed
    @Property("owner")
    private UUID ownerUniqueId;
    @Indexed
    private String name;
    private Set<Owner> members;
    private PlanetLocation spawnLocation;
    @Embedded
    private Atmosphere atmosphere;
    private boolean locked;
    private Map<String, Object> metaData;

    public BasicDatabasePlanet() {
        super();
    }

    public BasicDatabasePlanet(
            @NonNull final UUID uuid,
            @NonNull final UUID ownerUniqueId,
            @NonNull final String name,
            @NonNull final Set<Owner> members,
            @NonNull final PlanetLocation spawnLocation,
            @NonNull final Atmosphere atmosphere,
            @NonNull final boolean locked,
            @NonNull final Map<String, Object> metaData
    ) {
        super(uuid);
        this.ownerUniqueId = ownerUniqueId;
        this.name = name;
        this.members = members;
        this.spawnLocation = spawnLocation;
        this.atmosphere = atmosphere;
        this.locked = locked;
        this.metaData = metaData;
    }

    @Override
    @NotNull
    public Set<Owner> getMembers() {
        return members != null ? members : new HashSet<>();
    }

    @NotNull
    @Override
    public UUID getUniqueID() {
        return super.getUuid();
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public UUID getOwner() {
        return ownerUniqueId;
    }

    @NotNull
    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    @NotNull
    public PlanetLocation getSpawnLocation() {
        return spawnLocation;
    }

    @NotNull
    public Map<String, Object> getMetaData() {
        if (metaData == null) return Collections.emptyMap();
        else return metaData;
    }

    @Override
    public boolean getLocked() {
        return locked;
    }
}
