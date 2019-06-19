package de.astride.planetsystem.core.planet

import com.boydti.fawe.`object`.schematic.Schematic
import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.regions.CuboidRegion
import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.functions.toBukkitVector
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.Region
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.proxies.databaseHandler
import de.astride.planetsystem.api.proxies.gridHandler
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.api.proxies.players
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.location.BaseRegion
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class BaseLoadedPlanet(
    uniqueID: UniqueID,
    name: String,
    owner: Owner,
    members: MutableSet<Owner>,
    spawnLocation: PlanetLocation,
    atmosphere: Atmosphere,
    metaData: Map<String, Any>,
    middle: Location
) : LoadedPlanet, BasePlanet(uniqueID, name, owner, members, spawnLocation, atmosphere, metaData) {


    constructor(planet: Planet, middle: Location) : this(
        planet.uniqueID,
        planet.name,
        planet.owner,
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

    override var atmosphere: Atmosphere
        get() = super.atmosphere
        set(atmosphere) {
            super.atmosphere = atmosphere
            val (min, max) = (super.atmosphere.size - 1).toBukkitVector().generateMinAndMax()
            inner.min.vector = min
            inner.max.vector = max
        }

    init {

        inner = (atmosphere.size - 1).toBukkitVector().generateMinAndMax().toBaseRegion()
        outer = (atmosphere.maxSize - 1).toBukkitVector().generateMinAndMax().toBaseRegion()

    }

    override fun load(result: (LoadedPlanet) -> Unit) = super<LoadedPlanet>.load(result)

    override fun unload() {
        save()

        val distance = (atmosphere.size * 2).toDouble()
        val entities: Iterable<Entity> =
            middle.world.getNearbyEntities(middle, distance, distance, distance)

        entities.asSequence().filter { it is Player }.forEach {
            it.teleport(players.find(Owner(it.uniqueId))?.planet?.middle ?: return@forEach)
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
        val databasePlanet = BasicDatabasePlanet.by(this)
        databaseHandler.savePlanet(databasePlanet)
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

        if (inner != other.inner) return false
        if (outer != other.outer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = inner.hashCode()
        result = 31 * result + outer.hashCode()
        return result
    }

    override fun toString(): String = "BaseLoadedPlanet(inner=$inner, outer=$outer)"

}

private fun Vector.generateMinAndMax() = clone().multiply(-1) to clone()


