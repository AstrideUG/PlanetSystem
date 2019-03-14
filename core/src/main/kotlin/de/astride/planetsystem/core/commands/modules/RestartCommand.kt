package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.database.entities.BasicDatabasePlanet
import de.astride.planetsystem.core.log.MessageKeys
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.ChatColor
import org.bukkit.Material

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 14.03.2019 14:11.
 * Current Version: 1.0 (14.03.2019 - 14.03.2019)
 */
class RestartCommand : PlanetCommandModule {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 14.03.2019 14:11.
     * Current Version: 1.0 (14.03.2019 - 14.03.2019)
     */
    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) = if (planetPlayer.isOnHisPlanet()) {
        when {
            args.isEmpty() -> planetPlayer.player.openInventory(inventory)
            args.size == 1 && "confirmed".equals(args[0], true) -> {

                planetPlayer.player.kickPlayer("Planet deleted")
                Holder.instance.databaseHandler.deletePlanet(BasicDatabasePlanet.by(planetPlayer.planet))
                //Don't delete the schematic

            }
            else -> {
                /* TODO Send usage */
            }
        }

        planetPlayer.logger.success(MessageKeys.COMMANDS_INFO_SUCCESS)
    } else planetPlayer.logger.warn(MessageKeys.COMMANDS_RESTART_FAILED_NOT_OWN_PLANET)

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 14.03.2019 14:23.
     * Current Version: 1.0 (14.03.2019 - 14.03.2019)
     */
    companion object {

        /**
         * @author Lars Artmann | LartyHD
         * Created by Lars Artmann | LartyHD on 14.03.2019 14:23.
         * Current Version: 1.0 (14.03.2019 - 14.03.2019)
         */
        val inventory = InventoryBuilder(30, "${ChatColor.RED}Restart")
            .fillWith(
                ItemBuilder(Material.STAINED_GLASS_PANE, 14.toShort()).setName(ChatColor.RESET.toString()).build()
            )
            .setItem(29, Items.LEAVE.itemStack)
            .setItem(
                13, ItemBuilder(Material.REDSTONE_BLOCK).setName("${ChatColor.DARK_RED}Restart Planet").addLore(
                    "",
                    "",
                    "${ChatColor.DARK_RED}${ChatColor.BOLD}${ChatColor.UNDERLINE}ACHTUNG: Die Löschung ist unwiderruflich!",
                    "",
                    ""
                ).build()
            )
            .build()

    }

}
