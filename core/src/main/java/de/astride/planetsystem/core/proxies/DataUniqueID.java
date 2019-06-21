/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.proxies;

import de.astride.planetsystem.api.proxies.UniqueID;
import org.jetbrains.annotations.NotNull;
import xyz.morphia.annotations.Id;

import java.util.UUID;

/**
 * Created on 21.06.2019 02:42.
 *
 * @author Lars Artmann | LartyHD
 */
public class DataUniqueID implements UniqueID {

    @Id
    private final UUID uuid;

    @SuppressWarnings("unused") //used from morphia
    public DataUniqueID() {
        this(null);
    }

    public DataUniqueID(UUID uuid) {
        this.uuid = uuid;
    }

    @NotNull
    @Override
    public UUID getUuid() {
        return uuid;
    }

}
