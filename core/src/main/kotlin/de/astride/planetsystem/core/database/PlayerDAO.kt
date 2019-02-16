package de.astride.planetsystem.core.database

import xyz.morphia.Datastore
import xyz.morphia.dao.BasicDAO

class PlayerDAO(entityClass: Class<BasicDatabasePlayer>, ds: Datastore) :
    BasicDAO<BasicDatabasePlayer, String>(entityClass, ds)
