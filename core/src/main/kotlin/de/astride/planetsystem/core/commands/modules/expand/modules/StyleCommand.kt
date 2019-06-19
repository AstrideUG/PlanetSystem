package de.astride.planetsystem.core.commands.modules.expand.modules

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.commands.modules.expand.AtmosphereCommand
import de.astride.planetsystem.core.commands.modules.expand.sendUsage
import de.astride.planetsystem.core.functions.findPlanetOrMessage
import de.astride.planetsystem.core.functions.place
import de.astride.planetsystem.core.functions.replace
import de.astride.planetsystem.core.functions.toMaterial
import org.bukkit.Material

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:11.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class StyleCommand : PlanetCommandModule {

    private val permissions: Map<String, String> = mapOf()

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val player = planetPlayer.player
        if (args.isEmpty()) player.openInventory(AtmosphereCommand.Inventories.INVENTORY_STYLE)
        else if (args.size == 2) {
            val loadedPlanet = findPlanetOrMessage(Owner(player.uniqueId), player) ?: return

            if ("ID".toLowerCase() == args.firstOrNull()?.toLowerCase()) {

                //TODO add configs message if the Material was not found
                val material: Material = args[1].toMaterial() ?: return
                //TODO improve
                val permission = permissions["Planet.Command.Style.ID.$material"]
                    ?: permissions["Planet.Command.Style.ID.<Material>"].replace("<Material>", material)
                    ?: "Planet.Command.Style.ID.$material"

                if (player.hasPermission(permission)) {
                    @Suppress("DEPRECATION")
                    loadedPlanet.atmosphere = loadedPlanet.atmosphere.edit(blockID = material.id)
                    loadedPlanet.place()
                }


            } else if ("SubID".toLowerCase() == args.firstOrNull()?.toLowerCase()) {

                //TODO add configs message if the Material was not found
                val material = loadedPlanet.atmosphere.blockID.toMaterial() ?: return
                val subID = args[1].toInt()
                //TODO improve
                val permission = permissions["Planet.Command.Style.SubID.$material.$subID"]
                    ?: permissions["Planet.Command.Style.SubID.<Material>.<ID>"]
                        .replace("<Material>", material)
                        .replace("<ID>", subID)
                    ?: permissions["Planet.Command.Style.SubID.<Material>"]
                        .replace("<Material>", material)
                    ?: "Planet.Command.Style.SubID.$material.$subID"

                if (player.hasPermission(permission)) {
                    loadedPlanet.atmosphere = loadedPlanet.atmosphere.edit(blockDamage = subID)
                    loadedPlanet.place()
                }

            }

        } else planetPlayer.player.sendUsage()

    }

}