/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.planet

import com.boydti.fawe.`object`.schematic.Schematic
import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.regions.CuboidRegion
import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.functions.BukkitLocation
import de.astride.planetsystem.api.functions.toBukkitVector
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.gridHandler
import de.astride.planetsystem.api.holder.loadedPlanets
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.inline.planet
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.Region
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.planet.world
import de.astride.planetsystem.core.functions.delete
import de.astride.planetsystem.core.functions.toDatabasePlanet
import de.astride.planetsystem.core.location.BaseRegion
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class BaseLoadedPlanet(
    override val uniqueID: UniqueID,
    override val owner: Owner,
    override val name: String,
    override val members: MutableSet<Owner>,
    override var spawnLocation: PlanetLocation,
    atmosphere: Atmosphere,
    override val metaData: Map<String, Any>,
    middle: BukkitLocation
) : LoadedPlanet {

    constructor(planet: Planet, middle: Location) : this(
        planet.uniqueID,
        planet.owner,
        planet.name,
        planet.members,
        planet.spawnLocation,
        planet.atmosphere,
        planet.metaData,
        middle
    )

    override val middle = middle
        get() = field.clone()
    override val inner: Region
    override val outer: Region

    override val schematic: Schematic
        get() = Schematic(
            CuboidRegion(
                gridHandler.world.toWEWorld(),
                inner.min.toBukkitLocation(this).toWEVector(),
                inner.max.toBukkitLocation(this).toWEVector()
            )
        ).apply {
            clipboard!!.origin = middle.toWEVector()
        }

    override var atmosphere: Atmosphere = atmosphere
        set(atmosphere) {
            field = atmosphere

            val (min, max) = (field.size - 1).toBukkitVector().generateMinAndMax()
            inner.min.vector = min
            inner.max.vector = max
        }

    init {
        inner = (atmosphere.size - 1).toBukkitVector().generateMinAndMax().toBaseRegion()
        outer = (atmosphere.maxSize - 1).toBukkitVector().generateMinAndMax().toBaseRegion()
    }

    override fun load(result: (LoadedPlanet) -> Unit): Unit = result(this)

    override fun unload() {
        save()

        val distance = (atmosphere.size * 2).toDouble()
        val entities: Iterable<Entity> = world.getNearbyEntities(middle, distance, distance, distance)

        entities.asSequence().filter { it is Player }.forEach {
            val planet = Owner(it.uniqueId).planet ?: return@forEach
            it.teleport(planet.middle)
        }

        gridHandler.removeEntry(gridHandler.getId(middle))

        EditSessionBuilder(gridHandler.world.toWEWorld()).limitUnlimited().build().apply {
            val cuboidRegion = CuboidRegion(
                this.world,
                outer.min.toWEVector(this@BaseLoadedPlanet),
                outer.max.toWEVector(this@BaseLoadedPlanet)
            )
            setBlocks(cuboidRegion, @Suppress("DEPRECATION") BaseBlock(Material.AIR.id))
            flushQueue()
        }

        loadedPlanets -= this
    }

    override fun save() {
        delete()
        databaseHandler.savePlanet(this.toDatabasePlanet())
        DynamicNetworkFactory.dynamicNetworkAPI.saveSchematic(uniqueID.uuid, schematic)
    }


    @JvmName("toBaseRegion0")
    private fun Pair<PlanetLocation, PlanetLocation>.toBaseRegion() = BaseRegion(first, second)

    private fun Pair<Vector, Vector>.toBaseRegion() = toPlanetLocation().toBaseRegion()
    private fun Pair<Vector, Vector>.toPlanetLocation() = first.toPlanetLocation() to second.toPlanetLocation()
    private fun Vector.toPlanetLocation() = PlanetLocation(uniqueID, this, middle.yaw, middle.pitch)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseLoadedPlanet) return false

        if (uniqueID != other.uniqueID) return false
        if (owner != other.owner) return false
        if (name != other.name) return false
        if (members != other.members) return false
        if (spawnLocation != other.spawnLocation) return false
        if (metaData != other.metaData) return false
        if (inner != other.inner) return false
        if (outer != other.outer) return false
        if (atmosphere != other.atmosphere) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uniqueID.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + members.hashCode()
        result = 31 * result + spawnLocation.hashCode()
        result = 31 * result + metaData.hashCode()
        result = 31 * result + inner.hashCode()
        result = 31 * result + outer.hashCode()
        result = 31 * result + atmosphere.hashCode()
        return result
    }

    override fun toString(): String =
        "BaseLoadedPlanet(uniqueID=$uniqueID, owner=$owner, name='$name', members=$members, spawnLocation=$spawnLocation, metaData=$metaData, inner=$inner, outer=$outer, atmosphere=$atmosphere)"

}

private fun Vector.generateMinAndMax() = clone().multiply(-1) to clone()


