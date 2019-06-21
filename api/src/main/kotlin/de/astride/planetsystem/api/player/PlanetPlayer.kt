/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.player

import de.astride.planetsystem.api.database.OfflinePlayer
import de.astride.planetsystem.api.database.allMembers
import de.astride.planetsystem.api.functions.extensions.owner
import de.astride.planetsystem.api.functions.innerPlanet
import de.astride.planetsystem.api.location.isInside
import de.astride.planetsystem.api.location.relativeTo
import de.astride.planetsystem.api.log.Logger
import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.entity.Player

interface PlanetPlayer : OfflinePlayer {

    val player: Player
    val logger: Logger
    val planet: LoadedPlanet

    override val owner get() = player.owner
    override val planetUniqueId get() = planet.uniqueID

    fun unload()
    fun save()

}

fun PlanetPlayer.canEdit(location: Location): Boolean {
    return owner in location.innerPlanet?.allMembers.orEmpty()
}

fun PlanetPlayer.isOnHisPlanet(): Boolean = planet.outer.isInside(relativeTo())
