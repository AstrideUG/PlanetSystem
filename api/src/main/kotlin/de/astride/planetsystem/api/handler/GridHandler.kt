package de.astride.planetsystem.api.handler

import org.bukkit.Location

interface GridHandler {

	val maxSize: Int

	fun findEmptyLocation(): Location

	fun getId(location: Location): Int

	fun removeEntry(i: Int)

}
