package de.astride.planetsystem.core.database

import org.mongodb.morphia.Datastore
import org.mongodb.morphia.dao.BasicDAO

class PlayerDAO(entityClass: Class<BasicDatabasePlayer>, ds: Datastore) : BasicDAO<BasicDatabasePlayer, String>(entityClass, ds)
