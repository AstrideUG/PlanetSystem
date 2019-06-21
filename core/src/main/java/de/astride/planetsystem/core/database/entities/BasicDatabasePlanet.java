/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.atmosphere.Atmosphere;
import de.astride.planetsystem.api.database.DatabasePlanet;
import de.astride.planetsystem.api.location.PlanetLocation;
import de.astride.planetsystem.api.proxies.Owner;
import de.astride.planetsystem.api.proxies.UniqueID;
import de.astride.planetsystem.core.proxies.DataUniqueID;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity(value = "planets", noClassnameStored = true)
public class BasicDatabasePlanet implements DatabasePlanet {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uniqueID;
    @Indexed
    @Property("owner")
    private UUID ownerUniqueId;
    @Indexed
    private String name;
    private Set<UUID> members;
    private Set<UUID> banned;
    private PlanetLocation spawnLocation;
    @Embedded
    private Atmosphere atmosphere;
    private boolean locked;
    private Map<String, Object> metaData;

    @SuppressWarnings("unused") //used from morphia
    public BasicDatabasePlanet() {
        super();
    }

    public BasicDatabasePlanet(
            @NonNull final UniqueID uniqueID,
            @NonNull final Owner ownerUniqueId,
            @NonNull final String name,
            @NonNull final Set<Owner> members,
            @NonNull final Set<Owner> banned,
            @NonNull final PlanetLocation spawnLocation,
            @NonNull final Atmosphere atmosphere,
            @NonNull final boolean locked,
            @NonNull final Map<String, Object> metaData
    ) {
        this.uniqueID = uniqueID.getUuid();
        this.ownerUniqueId = ownerUniqueId.getUuid();
        this.name = name;
        this.members = members.stream().map(Owner::getUuid).collect(Collectors.toSet());
        this.banned = banned.stream().map(Owner::getUuid).collect(Collectors.toSet());
        this.spawnLocation = spawnLocation;
        this.atmosphere = atmosphere;
        this.locked = locked;
        this.metaData = metaData;
    }

    @Override
    @NotNull
    @NonNull
    public Set<Owner> getMembers() {
        return members != null ? members.stream().map(Owner::new).collect(Collectors.toSet()) : new HashSet<>();
    }

    @NotNull
    @NonNull
    public Set<Owner> getBanned() {
        return banned != null ? banned.stream().map(Owner::new).collect(Collectors.toSet()) : new HashSet<>();
    }

    @NotNull
    @NonNull
    @Override
    public UniqueID getUniqueID() {
        return new DataUniqueID(uniqueID);
    }

    @NotNull
    @NonNull
    public String getName() {
        return name;
    }

    @NotNull
    @NonNull
    public Owner getOwner() {
        return new Owner(ownerUniqueId);
    }

    @NotNull
    @NonNull
    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    @NotNull
    @NonNull
    public PlanetLocation getSpawnLocation() {
        return spawnLocation;
    }

    @NotNull
    @NonNull
    public Map<String, Object> getMetaData() {
        if (metaData == null) return Collections.emptyMap();
        else return metaData;
    }

    @Override
    public boolean getLocked() {
        return locked;
    }

}
