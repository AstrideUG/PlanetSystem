package de.astride.planetsystem.core.database

import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import xyz.morphia.Datastore
import xyz.morphia.dao.BasicDAO

class PlanetDAO(entityClass: Class<BasicDatabasePlanet>, ds: Datastore) :
    BasicDAO<BasicDatabasePlanet, String>(entityClass, ds)
