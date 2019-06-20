/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.database.DatabasePlayer;
import de.astride.planetsystem.core.database.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.IndexOptions;
import xyz.morphia.annotations.Indexed;

import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value = "players", noClassnameStored = true)
public class BasicDatabasePlayer extends DatabaseEntity implements DatabasePlayer {

    @Indexed(options = @IndexOptions(unique = true))
    private UUID planetUniqueId;

    public BasicDatabasePlayer(@NonNull final UUID uuid, @NonNull final UUID planetUniqueId) {
        super(uuid);
        this.planetUniqueId = planetUniqueId;
    }

    @NotNull
    @Override
    public UUID getUuid() {
        return super.getUuid();
    }

    @NotNull
    public UUID getPlanetUniqueId() {
        return planetUniqueId;
    }
}