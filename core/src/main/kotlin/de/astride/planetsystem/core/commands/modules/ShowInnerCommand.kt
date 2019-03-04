package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import org.bukkit.Material

class ShowInnerCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val planet = Holder.instance.loadedPlanets.find(planetPlayer.player.location)
        if (planet == null) {
            planetPlayer.logger.info("YouAreNotOnAPlanet")
            return
        }
        val inner = planet.inner
        inner.min.toBukkitLocation()?.block?.type = Material.REDSTONE_BLOCK
        inner.max.toBukkitLocation()?.block?.type = Material.REDSTONE_BLOCK
        inner.min.vector.midpoint(inner.max.vector)?.toLocation(Holder.instance.gridHandler.world)?.block?.type =
            Material.REDSTONE_BLOCK
    }

}
