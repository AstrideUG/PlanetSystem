package de.astride.planetsystem.api.planet

import com.boydti.fawe.`object`.schematic.Schematic
import de.astride.planetsystem.api.location.Region
import org.bukkit.Location

interface LoadedPlanet : Planet {

	val inner: Region
	val outer: Region

	val middle: Location

	val schematic: Schematic

	override fun load(result: (LoadedPlanet) -> Unit) = result(this)

}
