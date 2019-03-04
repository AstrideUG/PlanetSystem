package de.astride.planetsystem.core.commands.modules.expand.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.holder.isNotInHolderWorld
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.functions.replacePrice
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import org.bukkit.Bukkit
import org.bukkit.Material

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 06:49.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class InfoCommand : PlanetCommandModule {

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {

        val player = planetPlayer.player
        if (player.isNotInHolderWorld())
            player.sendConfigurableMessage("Planet.Command.Info.WorldIsNotSkyBlockWorld")
        else {

            //TODO: ADD 1 arg check

            val loadedPlanet = Holder.instance.loadedPlanets.find(player.location)
            if (loadedPlanet == null)
                player.sendConfigurableMessage("Planet.Command.Info.CanNotFindLoadedPlanetAtThatLocation")
            else {

                val center = loadedPlanet.middle
                val atmosphere = loadedPlanet.atmosphere

                player.sendMessage(
                    messages["Planet.Command.Info"]
                        ?.replacePrice(loadedPlanet)
                        .replace("<UUID>", loadedPlanet.owner.uuid)
                        .replace("<Owner>", Bukkit.getOfflinePlayer(loadedPlanet.owner.uuid)?.name)
                        .replace("<Middle.World>", center.world.name)
                        .replace("<Middle.X>", center.x)
                        .replace("<Middle.Y>", center.y)
                        .replace("<Middle.Z>", center.z)
                        .replace("<Size>", atmosphere.size)
                        .replace("<MaxSize>", atmosphere.maxSize)
                        .replace("<BlockID>", atmosphere.blockID)
                        .replace("<Material>", @Suppress("DEPRECATION") Material.getMaterial(atmosphere.blockID))
                        .replace("<BlockDamage>", atmosphere.blockDamage)
                )

            }

        }

    }

}
