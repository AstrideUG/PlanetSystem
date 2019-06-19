@file:Suppress("DEPRECATION")

package de.astride.planetsystem.core.commands.modules

import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.patterns.SingleBlockPattern
import de.astride.planetsystem.api.functions.toWEVector
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.gameWorld
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.utils.FaweUtils

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.06.2019 10:10.
 * Current Version: 1.0 (19.06.2019 - 19.06.2019)
 */
class FillInnerCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val planet = loadedPlanets.find(planetPlayer.player.location)
        if (planet == null) {
            planetPlayer.logger.info("YouAreNotOnAPlanet")
            return
        }
        val inner = planet.inner
        val min = inner.min.vector
        val max = inner.max.vector

        FaweUtils.setCuboid(gameWorld, min.toWEVector(), max.toWEVector(), SingleBlockPattern(BaseBlock(0)))

    }

}