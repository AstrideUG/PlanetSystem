package de.astride.planetsystem.core.holder.data

import com.boydti.fawe.FaweAPI
import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.regions.CuboidRegion
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkAPI
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.data.PlanetData
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import org.bukkit.Material
import java.util.*


//TODO: improve
class PlanetDataImpl(private val holder: Holder) : PlanetData {

	private val dynamicNetworkAPI: DynamicNetworkAPI get() = DynamicNetworkFactory.dynamicNetworkAPI
	override val loadedPlanets: MutableSet<LoadedPlanet> = mutableSetOf()

	override fun load(owner: UUID, request: (LoadedPlanet) -> Unit) =
			holder.databaseHandler.getPlanet(owner).toPlanet().load(request)

	//TODO: Move to LoadedPlanet
	override fun save(owner: UUID) {
		val loadedPlanet = holder.planetData.getLoadedPlanetByOwner(owner) ?: return
		val databasePlanet = BasicDatabasePlanet.by(loadedPlanet)
		dynamicNetworkAPI.saveSchematic(databasePlanet.uuid, loadedPlanet.schematic)
		holder.databaseHandler.savePlanet(databasePlanet)
	}

	//TODO: Move to LoadedPlanet
	override fun unload(owner: UUID) {
		save(owner)
		val planet = holder.playerData.getPlayer(owner)!!.planet
		holder.gridHandler.removeEntry(holder.gridHandler.getId(planet.middle))

		@Suppress("DEPRECATION")
		EditSessionBuilder(FaweAPI.getWorld(holder.world.name)).fastmode(true).build().apply {
			val cuboidRegion = CuboidRegion(this.world, planet.outer.min.toWEVector(), planet.outer.max.toWEVector())
			setBlocks(cuboidRegion, BaseBlock(Material.AIR.id))
			flushQueue()
		}

		loadedPlanets.remove(planet)
	}

}
