/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.commands.modules.expand.modules

import de.astride.planetsystem.api.functions.planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.functions.replacePlayer
import de.astride.planetsystem.core.functions.replacePrice
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendIfNotNull

/**
 * Created on 28.02.2019 06:49.
 * @author Lars Artmann | LartyHD
 */
class InfoCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {

        if (args.isNotEmpty()) {
            //TODO: send usage
            return
        }

        val player = planetPlayer.player
        val loadedPlanet = player.location.planet
        if (loadedPlanet == null)
            player.sendConfigurableMessage("Planet.Command.Info.CanNotFindLoadedPlanetAtThatLocation")
        else messages["Planet.Command.Info"]
            .replacePrice(loadedPlanet)
            .replacePlayer("", player.uniqueId)
            .replace("Middle", loadedPlanet.middle)
            .replace(atmosphere = loadedPlanet.atmosphere)
            .sendIfNotNull(player)

    }

}
