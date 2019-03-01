package de.astride.planetsystem.api.handler

import org.bukkit.Location
import org.bukkit.World

interface GridHandler {

    val maxSize: Int
    val world: World

    fun findEmptyLocation(): Location

    fun getId(location: Location): Int

    fun removeEntry(i: Int)

}
