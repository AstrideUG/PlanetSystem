package de.astride.planetsystem.core.planet

import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.functions.toWEWorld
import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.core.planets.SpherePlanet
import lombok.Data
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import org.bukkit.Location

@Data
open class BasePlanet(
    override val uniqueID: UniqueID,
    override val name: String,
    override val owner: Owner,
    override val members: MutableList<Owner>,
    override var size: Byte,
    override var spawnLocation: PlanetLocation
) : Planet {

    override fun load(result: (LoadedPlanet) -> Unit) {
        val grid = Holder.instance.gridHandler
        val location = grid.findEmptyLocation()

        val loadedPlanet = BaseLoadedPlanet(this, location, grid.maxSize)
        Holder.instance.planetData.loadedPlanets.add(loadedPlanet)

        DynamicNetworkFactory.dynamicNetworkAPI.getSchematic(this.uniqueID.uuid) { schematic ->

            schematic.paste(location.toWEWorld(), location.toWEVector())

            remove(loadedPlanet.owner)
            add(loadedPlanet.owner, loadedPlanet.middle, size)

            result(loadedPlanet)
        }

    }

    private fun add(uuid: Owner, islandLocation: Location, size: Byte) {
        val planet = SpherePlanet(uuid, islandLocation, size).apply { fix() }
        de.astride.planetsystem.core.planets.Planet.planets.add(planet)
    }

    private fun remove(planet: de.astride.planetsystem.core.planets.Planet) {
        de.astride.planetsystem.core.planets.Planet.planets.remove(planet)
    }

    private fun remove(uuid: Owner) {
        val planet = de.astride.planetsystem.core.planets.Planet.getPlanet(uuid, true) ?: return
        remove(planet)
    }


}
