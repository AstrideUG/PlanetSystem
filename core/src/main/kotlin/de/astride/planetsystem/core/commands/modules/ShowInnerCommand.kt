package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.gameWorld
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.core.commands.PlanetCommandModule
import org.bukkit.Material

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD.
 * Current Version: 1.0 (04.03.2019 - 18.03.2019)
 */
class ShowInnerCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val planet = loadedPlanets.find(planetPlayer.player.location)
        if (planet == null) {
            planetPlayer.logger.info("YouAreNotOnAPlanet")
            return
        }
        val inner = planet.inner
        val min = inner.min.toBukkitLocation() ?: return
        val max = inner.max.toBukkitLocation() ?: return
        min.block?.type = Material.REDSTONE_BLOCK
        max.block?.type = Material.REDSTONE_BLOCK
        val midpoint = min.toVector().getMidpoint(max.toVector()).toLocation(gameWorld)
        midpoint.block.type = Material.EMERALD_BLOCK
    }

}
