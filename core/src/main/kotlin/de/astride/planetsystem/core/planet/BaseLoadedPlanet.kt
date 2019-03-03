package de.astride.planetsystem.core.planet

import com.boydti.fawe.`object`.schematic.Schematic
import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.regions.CuboidRegion
import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.Region
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.core.database.DatabasePlanet
import de.astride.planetsystem.core.location.BaseRegion
import lombok.Data
import lombok.EqualsAndHashCode
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

@EqualsAndHashCode(callSuper = true)
@Data
class BaseLoadedPlanet(
    uniqueID: UniqueID,
    name: String,
    owner: Owner,
    members: MutableList<Owner>,
    spawnLocation: PlanetLocation,
    atmosphere: Atmosphere,
    middle: Location
) : LoadedPlanet, BasePlanet(uniqueID, name, owner, members, spawnLocation, atmosphere) {


    constructor(planet: Planet, middle: Location) : this(
        planet.uniqueID,
        planet.name,
        planet.owner,
        planet.members,
        planet.spawnLocation,
        planet.atmosphere,
        middle
    )

    override val middle = middle
        get() = field.clone()
    override val inner: Region
    override val outer: Region

    override val schematic: Schematic
        get() = Schematic(
            CuboidRegion(
                holder.gridHandler.world.toWEWorld(),
                inner.min.toBukkitLocation(middle.toVector()).toWEVector(),
                inner.max.toBukkitLocation(middle.toVector()).toWEVector()
            )
        ).apply {
            clipboard!!.origin = middle.toWEVector()
        }

    private val holder get() = Holder.instance

    override var atmosphere: Atmosphere
        get() = super.atmosphere
        set(atmosphere) {
            super.atmosphere = atmosphere
            val (min, max) = super.atmosphere.size.toBukkitVector().generateMinAndMax()
            inner.min.vector = min
            inner.max.vector = max
        }

    init {

        val region = atmosphere.size.toBukkitVector().generateMinAndMax().toBaseRegion()

        inner = region
        outer = region

    }

    override fun load(result: (LoadedPlanet) -> Unit) = super<LoadedPlanet>.load(result)

    override fun unload() {
        save()

        val distance = (atmosphere.size * 2).toDouble()
        val entities: Iterable<Entity> =
            middle.world.getNearbyEntities(middle, distance, distance, distance)

        entities.filter { it is Player }.forEach {
            it.teleport(holder.find(Owner(it.uniqueId))?.middle ?: return@forEach)
        }

        holder.gridHandler.removeEntry(holder.gridHandler.getId(middle))

        EditSessionBuilder(holder.gridHandler.world.toWEWorld()).fastmode(true).build().apply {
            val cuboidRegion = CuboidRegion(this.world, outer.min.toWEVector(), outer.max.toWEVector())
            setBlocks(cuboidRegion, @Suppress("DEPRECATION") (BaseBlock(Material.AIR.id)))
            flushQueue()
        }

        holder.loadedPlanets -= this
    }

    override fun save() {
        val databasePlanet = DatabasePlanet.by(this)
        println("hallo")
        DynamicNetworkFactory.dynamicNetworkAPI.saveSchematic(uniqueID.uuid, schematic)
        DynamicNetworkFactory.dynamicNetworkAPI.hasSchematic(uniqueID.uuid) {
            println("hasSchematic ${uniqueID.uuid}: $it")
        }
        holder.databaseHandler.savePlanet(databasePlanet)
    }


    @JvmName("toBaseRegion0")
    private fun Pair<PlanetLocation, PlanetLocation>.toBaseRegion() = BaseRegion(first, second)

    private fun Pair<Vector, Vector>.toBaseRegion() = toPlanetLocation().toBaseRegion()
    private fun Pair<Vector, Vector>.toPlanetLocation() = first.toPlanetLocation() to second.toPlanetLocation()
    private fun Vector.toPlanetLocation() = PlanetLocation(uniqueID, this, middle.yaw, middle.pitch)
}

//TODO Add to Darkness
private fun Number.toBukkitVector() = toDouble().run { Vector(this, this, this) }

private fun Vector.generateMinAndMax() = clone().multiply(-1) to clone().subtract(1.toBukkitVector())


