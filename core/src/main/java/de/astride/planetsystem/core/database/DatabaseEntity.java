package de.astride.planetsystem.core.database;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.IndexOptions;
import xyz.morphia.annotations.Indexed;

import java.util.UUID;

/**
 * Created by DevSnox on 12.02.18
 * Copyright (c) 2018 DevSnox
 * GitHub: https://github.com/DevSnox
 * Web: http://devsnox.me
 * Mail: me.devsnox@gmail.com
 * Discord: DevSnox#4884 | Skype: live:chaos3729
 * <p>
 * Last edit 14.03.2019 from Lars Artmann | LartyHD
 */
@NoArgsConstructor
@Data
public abstract class DatabaseEntity {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;

    public DatabaseEntity(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
