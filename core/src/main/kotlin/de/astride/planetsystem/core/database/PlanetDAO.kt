package de.astride.planetsystem.core.database

import xyz.morphia.Datastore
import xyz.morphia.dao.BasicDAO

class PlanetDAO(entityClass: Class<DatabasePlanet>, ds: Datastore) : BasicDAO<DatabasePlanet, String>(entityClass, ds)
