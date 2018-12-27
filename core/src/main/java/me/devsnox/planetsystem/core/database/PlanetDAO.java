package me.devsnox.planetsystem.core.database;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class PlanetDAO extends BasicDAO<DatabasePlanet, String> {

    public PlanetDAO(final Class<DatabasePlanet> entityClass, final Datastore ds) {
        super(entityClass, ds);
    }
}
