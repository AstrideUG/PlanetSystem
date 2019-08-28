/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database

import de.astride.planetsystem.core.database.entities.BasicOfflinePlayer
import xyz.morphia.Datastore
import xyz.morphia.dao.BasicDAO

class PlayerDAO(entityClass: Class<BasicOfflinePlayer>, ds: Datastore) :
    BasicDAO<BasicOfflinePlayer, String>(entityClass, ds)
