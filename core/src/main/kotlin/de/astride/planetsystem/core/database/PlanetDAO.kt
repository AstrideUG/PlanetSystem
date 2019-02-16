package de.astride.planetsystem.core.database

import org.mongodb.morphia.Datastore
import org.mongodb.morphia.dao.BasicDAO

class PlanetDAO(entityClass: Class<DatabasePlanet>, ds: Datastore) : BasicDAO<DatabasePlanet, String>(entityClass, ds)
