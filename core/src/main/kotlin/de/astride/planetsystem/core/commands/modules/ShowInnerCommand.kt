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
        val min = inner.min.toBukkitLocation() ?: return
        val max = inner.max.toBukkitLocation() ?: return
        min.block?.type = Material.REDSTONE_BLOCK
        max.block?.type = Material.REDSTONE_BLOCK
        val midpoint = min.toVector().getMidpoint(max.toVector()).toLocation(Holder.instance.gridHandler.world)
        midpoint.block.type = Material.EMERALD_BLOCK
    }

}
