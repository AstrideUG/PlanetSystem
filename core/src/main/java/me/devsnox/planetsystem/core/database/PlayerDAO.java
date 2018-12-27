package me.devsnox.planetsystem.core.database;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class PlayerDAO extends BasicDAO<DatabasePlayer, String> {

    public PlayerDAO(Class<DatabasePlayer> entityClass, Datastore ds) {
        super(entityClass, ds);
    }
}
