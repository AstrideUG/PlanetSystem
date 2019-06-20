/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database;

import lombok.Data;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.IndexOptions;
import xyz.morphia.annotations.Indexed;

import java.util.UUID;

@Data
public abstract class DatabaseEntity {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;

    public DatabaseEntity() {
    }

    public DatabaseEntity(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

}
