package de.astride.planetsystem.core.database

import xyz.morphia.Datastore
import xyz.morphia.dao.BasicDAO

class PlayerDAO(entityClass: Class<DatabasePlayer>, ds: Datastore) : BasicDAO<DatabasePlayer, String>(entityClass, ds)
