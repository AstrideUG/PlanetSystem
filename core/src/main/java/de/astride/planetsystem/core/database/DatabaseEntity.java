package de.astride.planetsystem.core.database;

import lombok.Data;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.UUID;

/**
 * Created by DevSnox on 12.02.18
 * Copyright (c) 2018 DevSnox
 * GitHub: https://github.com/DevSnox
 * Web: http://devsnox.me
 * Mail: me.devsnox@gmail.com
 * Discord: DevSnox#4884 | Skype: live:chaos3729
 */
@Data
public abstract class DatabaseEntity {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;

    public DatabaseEntity() {

    }

    public DatabaseEntity(UUID uuid) {
        this.uuid = uuid;
    }
}
