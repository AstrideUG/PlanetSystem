/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.planet

import com.boydti.fawe.`object`.schematic.Schematic
import de.astride.planetsystem.api.functions.BukkitLocation
import de.astride.planetsystem.api.functions.BukkitWorld
import de.astride.planetsystem.api.location.Region

interface LoadedPlanet : Planet {

    val inner: Region
    val outer: Region

    val middle: BukkitLocation
    val schematic: Schematic

    //TODO: override suspend fun load(): LoadedPlanet = this
    override fun load(result: (LoadedPlanet) -> Unit)

    /*TODO suspend*/ fun unload()

    /*TODO suspend*/ fun save()

}

val LoadedPlanet.world: BukkitWorld get() = middle.world
