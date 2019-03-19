package de.astride.planetsystem.core.commands.modules.expand

import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.commands.modules.expand.modules.ExpandCommand
import de.astride.planetsystem.core.commands.modules.expand.modules.InfoCommand
import de.astride.planetsystem.core.commands.modules.expand.modules.ShapeCommand
import de.astride.planetsystem.core.commands.modules.expand.modules.StyleCommand
import de.astride.planetsystem.core.log.MessageKeys
import de.astride.planetsystem.core.proxies.config
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.event.inventory.InventoryType
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 06:46.
 * Current Version: 1.0 (28.02.2019 - 18.03.2019)
 */
class ExpandCommand : PlanetCommandModule {

    private val commandModules: MutableMap<String, PlanetCommandModule> = HashMap()
//    override val usage: Array<String>
//        get() = arrayOf(ConfigService.instance.config.planetCommand) ?: super.usage

    init {
        arrayOf(
            ExpandCommand(),
            InfoCommand(),
            ShapeCommand(),
            StyleCommand()
        ).forEach { command ->
            command.usage.forEach { commandModules[it.toLowerCase()] = command }
        }
    }

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        when {
            !planetPlayer.isOnHisPlanet() -> planetPlayer.logger.warn(MessageKeys.COMMANDS_EXPAND_FAILED_NOT_ON_OWN_PLANET)
            args.isEmpty() -> planetPlayer.player.openInventory(Inventories.INVENTORY_MAIN)
            args[0].isModule() ->
                commandModules[args[0].toLowerCase()]?.execute(planetPlayer, args.drop(1).toTypedArray())
            else -> planetPlayer.logger.warn(MessageKeys.COMMANDS_MAIN_HELP)
        }
    }

//    override fun permissions(args: Array<String>): String = super.permissions(args).run {
//        if (args[0].isModule()) "$this.${commandModules[args[0].toLowerCase()]}" else this
//    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 28.02.2019 07:18.
     * Current Version: 1.0 (28.02.2019 - 28.02.2019)
     */
    override fun permissions(args: Array<String>): String {
        val result = super.permissions(args)
        return if (args.isNotEmpty() && args[0].isModule()) "$result.${commandModules[args[0].toLowerCase()]}" else result
    }

    private fun String.isModule() = commandModules.containsKey(toLowerCase())

    object Inventories {
        val INVENTORY_MAIN =
            InventoryBuilder(InventoryType.BREWING, messages["Planet.Command.NoArgs.Inventory.Name"].toString())
                .setDesign()
                .setItem(
                    0, ItemBuilder(Material.FIREBALL)
                        .setName(messages["Planet.Command.NoArgs.Inventory.Entry.Shape"] ?: "Error")
                        .build()
                )
                .setItem(
                    1, ItemBuilder(Material.FIREWORK_CHARGE)
                        .addAllItemFlags()
                        .setName(messages["Planet.Command.NoArgs.Inventory.Entry.Expand"] ?: "Error")
                        .setFireworkEffectMetaEffect(
                            FireworkEffect.builder().withColor(Color.ORANGE).withColor(Color.YELLOW).withColor(
                                Color.ORANGE
                            ).build()
                        )
                        .build()
                )
                .setItem(
                    2, ItemBuilder(Material.INK_SACK, 14.toShort())
                        .setName(messages["Planet.Command.NoArgs.Inventory.Entry.Style"] ?: "Error")
                        .build()
                )
                .setItem(
                    3, ItemBuilder(Material.SIGN)
                        .setName(messages["Planet.Command.NoArgs.Inventory.Entry.Info"] ?: "Error")
                        .build()
                )
                .build()
        val INVENTORY_SHAPE = InventoryBuilder(
            InventoryType.HOPPER,
            messages["Planet.Command.Shape.Inventory.Name"]
                ?: messages["Planet.Inventory.Entry.Shape"]
                ?: "Error"
        )
            .setDesign()
            .setItem(
                2, ItemBuilder(Material.BRICK)
                    .setName(messages["Planet.Command.Shape.Inventory.Entry.Cube"] ?: "Error")
                    .build()
            )
            .build()
        val INVENTORY_STYLE = InventoryBuilder(
            54,
            messages["Planet.Command.Style.Inventory.Name"]
                ?: messages["Planet.Command.Style.Inventory.Entry.Style"]
                ?: "Error"
        ).setDesign().apply {
            for (i in 0..2) setItem(
                12 + i,
                ItemBuilder(Material.STAINED_GLASS, i.toShort()).setName(ChatColor.GREEN.toString()).build()
            )
            for (i in 0..4) setItem(
                20 + i,
                ItemBuilder(Material.STAINED_GLASS, (i + 3).toShort()).setName(ChatColor.GREEN.toString()).build()
            )
            for (i in 0..4) setItem(
                29 + i,
                ItemBuilder(Material.STAINED_GLASS, (i + 8).toShort()).setName(ChatColor.GREEN.toString()).build()
            )
            for (i in 0..2) setItem(
                39 + i,
                ItemBuilder(Material.STAINED_GLASS, (i + 13).toShort()).setName(ChatColor.GREEN.toString()).build()
            )
        }.build()
    }

}

fun CommandSender.sendUsage() = Bukkit.dispatchCommand(this, config.planetCommand)