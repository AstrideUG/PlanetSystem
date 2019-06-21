/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.database.OfflinePlayer;
import de.astride.planetsystem.api.proxies.Owner;
import de.astride.planetsystem.api.proxies.UniqueID;
import de.astride.planetsystem.core.proxies.DataUniqueID;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.IndexOptions;
import xyz.morphia.annotations.Indexed;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity(value = "players", noClassnameStored = true)
public class BasicOfflinePlayer implements OfflinePlayer {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID owner;
    @Indexed(options = @IndexOptions(unique = true))
    private UUID planetUniqueId;
    private Set<UUID> history;

    @SuppressWarnings("unused") //used from morphia
    public BasicOfflinePlayer() {
        super();
    }

    public BasicOfflinePlayer(
            @NonNull final Owner owner,
            @NonNull final UniqueID planetUniqueId,
            @NonNull final Set<UniqueID> history
    ) {
        this.owner = owner.getUuid();
        this.planetUniqueId = planetUniqueId.getUuid();
        this.history = history.stream().map(UniqueID::getUuid).collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public Owner getOwner() {
        return new Owner(owner);
    }

    @NotNull
    @Override
    public UniqueID getPlanetUniqueId() {
        return new DataUniqueID(planetUniqueId);
    }

    @NotNull
    @Override
    public Set<UniqueID> getHistory() {
        return history != null ? history.stream().map(DataUniqueID::new).collect(Collectors.toSet()) : new HashSet<>();
    }

}