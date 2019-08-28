/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

@file:Suppress("unused")

package de.astride.planetsystem.api.planet

import com.boydti.fawe.`object`.schematic.Schematic
import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.functions.BukkitLocation
import de.astride.planetsystem.api.functions.BukkitWorld
import de.astride.planetsystem.api.location.Region
import de.astride.planetsystem.api.location.isInside
import org.bukkit.entity.Player

interface LoadedPlanet : OfflinePlanet {

    val inner: Region
    val outer: Region

    val middle: BukkitLocation
    val schematic: Schematic

    /*TODO suspend*/ fun unload()

    /*TODO suspend*/ fun save()

}

val LoadedPlanet.world: BukkitWorld get() = middle.world

val LoadedPlanet.players: Set<Player> get() = world.players.filter { outer.isInside(it.location.toVector()) }.toSet()
val LoadedPlanet.isEmpty: Boolean get() = world.players.none { outer.isInside(it.location.toVector()) }
val LoadedPlanet.isNotEmpty: Boolean get() = world.players.any { outer.isInside(it.location.toVector()) }
