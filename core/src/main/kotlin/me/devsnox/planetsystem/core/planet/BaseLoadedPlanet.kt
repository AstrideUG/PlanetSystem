package me.devsnox.planetsystem.core.planet

import com.boydti.fawe.`object`.schematic.Schematic
import com.sk89q.worldedit.regions.CuboidRegion
import lombok.Data
import lombok.EqualsAndHashCode
import me.devsnox.planetsystem.api.functions.toWEVector
import me.devsnox.planetsystem.api.functions.toWEWorld
import me.devsnox.planetsystem.api.holder.Holder.Impl.holder
import me.devsnox.planetsystem.api.location.PlanetLocation
import me.devsnox.planetsystem.api.location.Region
import me.devsnox.planetsystem.api.planet.LoadedPlanet
import me.devsnox.planetsystem.api.planet.Planet
import me.devsnox.planetsystem.core.location.BaseRegion
import org.bukkit.Location
import org.bukkit.util.Vector

@EqualsAndHashCode(callSuper = true)
@Data
class BaseLoadedPlanet(
		planet: Planet,
		middle: Location,
		maxSize: Int
) : LoadedPlanet, BasePlanet(planet.uniqueID, planet.name, planet.ownerUniqueID, planet.members, planet.size, planet.spawnLocation) {

	override val middle = middle
		get() = field.clone()
	override val inner: Region
	override val outer: Region

	override val schematic: Schematic
		get() = Schematic(CuboidRegion(holder.world.toWEWorld(), inner.min.toWEVector(), inner.max.toWEVector())).apply {
			clipboard!!.origin = middle.toWEVector()
		}

	/**
	 * @author Lars Artmann | LartyHD
	 * Created by Lars Artmann | LartyHD on 12.02.2019 17:55.
	 * Current Version: 1.0 (12.02.2019 - 12.02.2019)
	 */
	override var size: Byte
		get() = super.size
		set(size) {
			super.size = size
			val vector = Vector(size.toInt(), size - 1, size.toInt())
			inner.max.vector = vector.subtract(Vector(1, 0, 1))
			inner.min.vector = vector.clone().multiply(-1)
		}

	init {

		val innerMin = size.toBukkitVector().multiply(-1).toPlanetLocation()
		val innerMax = size.toBukkitVector().subtract(Vector(1, 1, 1)).toPlanetLocation()

		val outerMin = maxSize.toBukkitVector().multiply(-1).toPlanetLocation()
		val outerMax = maxSize.toBukkitVector().toPlanetLocation()

		inner = BaseRegion(innerMin, innerMax)
		outer = BaseRegion(outerMin, outerMax)

	}

	override fun load(result: (LoadedPlanet) -> Unit) = super<LoadedPlanet>.load(result)

	//TODO Add to Darkness
	@Suppress("MemberVisibilityCanBePrivate")
	fun Number.toBukkitVector() = toDouble().run { Vector(this, this, this) }

	private fun Vector.toPlanetLocation() = PlanetLocation(uniqueID, this, middle.yaw, middle.pitch)

}
