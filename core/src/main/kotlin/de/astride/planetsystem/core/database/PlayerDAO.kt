package de.astride.planetsystem.core.database

import de.astride.planetsystem.core.database.entities.BasicDatabasePlayer
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.dao.BasicDAO

class PlayerDAO(entityClass: Class<BasicDatabasePlayer>, ds: Datastore) : BasicDAO<BasicDatabasePlayer, String>(entityClass, ds)
