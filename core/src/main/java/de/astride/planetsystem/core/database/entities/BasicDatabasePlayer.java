/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.database.DatabasePlayer;
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

import java.util.UUID;

@Data
@Entity(value = "players", noClassnameStored = true)
public class BasicDatabasePlayer implements DatabasePlayer {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID owner;
    @Indexed(options = @IndexOptions(unique = true))
    private UUID planetUniqueId;

    @SuppressWarnings("unused") //used from morphia
    public BasicDatabasePlayer() {
        super();
    }

    public BasicDatabasePlayer(@NonNull final Owner owner, @NonNull final UniqueID planetUniqueId) {
        this.owner = owner.getUuid();
        this.planetUniqueId = planetUniqueId.getUuid();
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

}