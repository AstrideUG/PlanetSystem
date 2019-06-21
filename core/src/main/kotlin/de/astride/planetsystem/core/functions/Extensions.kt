/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.database.DatabasePlayer
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.database.entities.BasicDatabasePlayer

/*
 * Created on 20.06.2019 01:39.
 * @author Lars Artmann | LartyHD
 */

fun Planet.toDatabasePlanet(): DatabasePlanet = BasicDatabasePlanet(
    uniqueID,
    owner,
    name,
    members,
    banned,
    spawnLocation,
    atmosphere,
    locked,
    metaData
)

fun Planet.toDatabasePlayer(): DatabasePlayer = BasicDatabasePlayer(owner, uniqueID)
