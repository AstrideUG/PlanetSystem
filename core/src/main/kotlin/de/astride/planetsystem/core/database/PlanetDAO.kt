/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.database

import de.astride.planetsystem.core.database.entities.BasicOfflinePlanet
import xyz.morphia.Datastore
import xyz.morphia.dao.BasicDAO

class PlanetDAO(entityClass: Class<BasicOfflinePlanet>, ds: Datastore) :
    BasicDAO<BasicOfflinePlanet, String>(entityClass, ds)
