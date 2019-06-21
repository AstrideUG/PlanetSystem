/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.database.OfflinePlayer
import de.astride.planetsystem.core.database.entities.BasicOfflinePlanet
import de.astride.planetsystem.core.database.entities.BasicOfflinePlayer

/*
 * Created on 20.06.2019 01:39.
 * @author Lars Artmann | LartyHD
 */

fun OfflinePlayer.toBasicOfflinePlayer(): BasicOfflinePlayer = BasicOfflinePlayer(owner, planetUniqueId, history)
fun OfflinePlanet.toBasicOfflinePlayer(): BasicOfflinePlanet =
    BasicOfflinePlanet(uniqueID, owner, name, members, banned, spawnLocation, atmosphere, locked, metaData)
