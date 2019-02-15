package me.devsnox.planetsystem.api.location

import lombok.NoArgsConstructor
import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.*

@NoArgsConstructor
data class PlanetLocation(
		var planetID: UUID,
		var vector: Vector = Vector(),
		var yaw: Float = 0f,
		var pitch: Float = 0f
) {

	constructor(planetID: UUID, location: Location) : this(planetID, location.toVector(), location.yaw, location.pitch)
	constructor(planet: LoadedPlanet, location: Location) :
			this(planet.uniqueID, location.clone().subtract(planet.middle) /* Location - middle-point */)

}

fun PlanetLocation.toBukkitLocation(input: Vector): Location = vector.clone().add(input).toLocation(holder.world).also {
	it.yaw = yaw
	it.pitch = pitch
}

fun PlanetLocation.toBukkitLocation(): Location = toBukkitLocation(holder.planetData.getLoadedPlanet(planetID)!!.middle.toVector())
