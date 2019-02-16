package de.astride.planetsystem.core.database

import org.mongodb.morphia.Datastore
import org.mongodb.morphia.dao.BasicDAO

class PlanetDAO(entityClass: Class<BasicDatabasePlanet>, ds: Datastore) : BasicDAO<BasicDatabasePlanet, String>(entityClass, ds)
