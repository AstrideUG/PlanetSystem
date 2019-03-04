package de.astride.planetsystem.core.commands.modules.expand.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.holder.isNotInGameWorld
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.functions.replacePlayer
import de.astride.planetsystem.core.functions.replacePrice
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendIfNotNull

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 06:49.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class InfoCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {

        val player = planetPlayer.player
        if (player.isNotInGameWorld())
            player.sendConfigurableMessage("Planet.Command.Info.WorldIsNotSkyBlockWorld")
        else {

            //TODO: ADD 1 arg check
            val loadedPlanet = Holder.instance.loadedPlanets.find(player.location)
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

}
