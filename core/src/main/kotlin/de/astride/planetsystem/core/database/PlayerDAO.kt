package de.astride.planetsystem.core.database

import org.mongodb.morphia.Datastore
import org.mongodb.morphia.dao.BasicDAO

class PlayerDAO(entityClass: Class<DatabasePlayer>, ds: Datastore) : BasicDAO<DatabasePlayer, String>(entityClass, ds)
