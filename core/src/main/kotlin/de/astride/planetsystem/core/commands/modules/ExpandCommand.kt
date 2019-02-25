/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */


package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.planets.Planet
import de.astride.planetsystem.core.planets.SpherePlanet
import de.astride.planetsystem.core.service.ConfigService
import de.astride.planetsystem.core.utils.EconomyUtils
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import net.darkdevelopers.darkbedrock.darkness.universal.builder.textcomponent.builder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.plugin.java.JavaPlugin
import kotlin.concurrent.thread

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 25.10.2018 03:20.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 01.12.2018
 */
class ExpandCommand(javaPlugin: JavaPlugin, private val permissions: Map<String, String>) : Command(
    javaPlugin,
    ConfigService.instance.config.planetCommand,
    maxLength = 3,
    usage = "[Info]:${permissions["Planet.Command.Info"]}" +
            "|[Expand]:${permissions["Planet.Command.Expand"]}" +
            "|[Expand Chat]:${permissions["Planet.Command.Expand.Chat"]}" +
            "|[Expand Force]:${permissions["Planet.Command.Expand.Force"]}" +
            "|[Shape]:${permissions["Planet.Command.Shape"]}" +
            "|[Shape Cube]:${permissions["Planet.Command.Shape.Cube"]}" +
            "|[Style]:${permissions["Planet.Command.Style"]}" +
            "|[Style UniqueID <UniqueID>]:${permissions["Planet.Command.Style.UniqueID.<Material>"]}" +
            "|[Style SubID <UniqueID>]:${permissions["Planet.Command.Style.SubID.<Material>.<UniqueID>"]}"
) {

    private val delay: MutableSet<Player> = HashSet()
    private val mainInventory =
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
    private val shapeInventory = InventoryBuilder(
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
    private val styleInventory = InventoryBuilder(
        54,
        messages["Planet.Command.Style.Inventory.Name"]
            ?: messages["Planet.Command.Style.Inventory.Entry.Style"]
            ?: "Error"
    ).setDesign().apply {
        for (i in 0..2) setItem(
            12 + i,
            ItemBuilder(Material.STAINED_GLASS, i.toShort()).setName(ChatColor.BLACK.toString()).build()
        )
        for (i in 0..4) setItem(
            20 + i,
            ItemBuilder(Material.STAINED_GLASS, (i + 3).toShort()).setName(ChatColor.BLACK.toString()).build()
        )
        for (i in 0..4) setItem(
            29 + i,
            ItemBuilder(Material.STAINED_GLASS, (i + 8).toShort()).setName(ChatColor.BLACK.toString()).build()
        )
        for (i in 0..2) setItem(
            39 + i,
            ItemBuilder(Material.STAINED_GLASS, (i + 13).toShort()).setName(ChatColor.BLACK.toString()).build()
        )
    }.build()


    override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer { player ->
        //TODO ADD IS ON HIS ISLAND CHECK
        //TODO ADD EXPAND ANIMATION
        //TODO Reload
        when {
            args.isEmpty() -> argsEmpty(player)
            1 == args.size -> args1(player, args[0])
            2 == args.size -> args2(player, args[0], args[1])
            3 == args.size -> args3(player, args[0], args[1], args[2])
            else -> sendUseMessage(sender)
        }
    }

    private fun argsEmpty(player: Player) {
        player.openInventory(mainInventory)
    }

    private fun args1(player: Player, arg1: String) {
        @Suppress("unused")
        fun String.isSameAsArg1() = arg1.equals(this, true)
        when {
            "Top".isSameAsArg1() -> {
                //TODO
                player.sendConfigurableMessage("Planet.Command.Top.Loading")

                val a = arrayOf<Planet>()
                Planet.planets.forEach {

                }
                Planet.planets.associateWith {

                }
                return
            }
            "List".isSameAsArg1() -> {
                //TODO
                player.sendMessage("Alle Planeten:")
                Planet.planets.forEach { player.sendMessage(it.toString()) }
                return
            }
            "Info".isSameAsArg1() -> player.hasPermissionBy("Planet.Command.Info") {
                if (player.location.world != Holder.instance.world) {
                    player.sendMessage(messages["Planet.Command.Info.WorldIsNotSkyBlockWorld"])
                    return
                }
                val owner = Holder.instance.planetData.getPlanet(player.location)?.owner ?: return@hasPermissionBy
                val planet: Planet = Planet.getPlanetWithMessageOnFail(owner, player, true) ?: return
                val center = planet.center
                player.sendMessage(
                    messages["Planet.Command.Info"]
                        ?.replacePrice(planet)
                        ?.replace("<Owner>", Bukkit.getOfflinePlayer(planet.owner.uuid).name.toString(), true)
                        ?.replace("<Center.World>", center.world.name, true)
                        ?.replace("<Center.X>", center.x.toString(), true)
                        ?.replace("<Center.Y>", center.y.toString(), true)
                        ?.replace("<Center.Z>", center.z.toString(), true)
                        ?.replace("<UUID>", planet.owner.toString(), true)
                        ?.replace("<Size>", planet.size.toString(), true)
                        ?.replace("<MaxSize>", planet.maxSize.toString(), true)
                        ?.replace("<BlockID>", planet.blockID.toString(), true)
                        ?.replace(
                            "<Material>",
                            @Suppress("DEPRECATION") Material.getMaterial(planet.blockID).toString(),
                            true
                        )
                        ?.replace("<BlockDamage>", planet.blockDamage.toString(), true)
                )
                return
            }
            "Expand".isSameAsArg1() -> player.hasPermissionBy("Planet.Command.Expand") {
                val planet = Planet.getPlanetWithMessageOnFail(Owner(player.uniqueId), player) ?: return
                val price = planet.getPrice()
                player.openInventory(
                    InventoryBuilder(
                        player,
                        InventoryType.DISPENSER,
                        messages["Planet.Command.Expand.Inventory.Name"]
                            ?: messages["Planet.Command.NoArgs.Inventory.Entry.Expand"]
                            ?: "Error"
                    )
                        .setDesign()
                        .setItem(
                            4, ItemBuilder(Material.FIREWORK_CHARGE)
                                .addAllItemFlags()
                                .setName(
                                    messages["Planet.Command.Expand.Inventory.Entry.Expand"]
                                        ?: messages["Planet.Command.NoArgs.Inventory.Entry.Expand"]
                                        ?: "Error"
                                )
                                .setFireworkEffectMetaEffect(FireworkEffect.builder().withColor(Color.RED).build())
                                .addLore(
                                    "${Colors.TEXT}Preis${Colors.IMPORTANT}: ${
                                    if (EconomyUtils.economy.has(player, price)) Colors.PRIMARY else ChatColor.RED
                                    }$price"
                                )
                                .build()
                        )
                        .build()
                )
                return
            }
            "Shape".isSameAsArg1() -> player.hasPermissionBy("Planet.Command.Shape") {
                player.openInventory(shapeInventory)
                return
            }
            "Style".isSameAsArg1() -> player.hasPermissionBy("Planet.Command.Style") {
                player.openInventory(styleInventory)
                return
            }
        }
        sendUseMessage(player)
    }

    @Suppress("DEPRECATION")
    private fun args2(player: Player, arg1: String, arg2: String) {
        fun String.isSameAsArg1() = this.isSame(arg1)
        fun String.isSameAsArg2() = this.isSame(arg2)
        val owner = Owner(player.uniqueId)
        val planet: Planet = Planet.getPlanetWithMessageOnFail(owner, player, true) ?: return
        when {
            "Expand".isSameAsArg1() -> {
                if ("Chat".isSameAsArg2()) player.hasPermissionBy("Planet.Command.Expand.Chat") {
                    player.spigot().sendMessage(
                        TextComponent(messages["Planet.Command.Expand.Chat"]?.replacePrice(planet))
                            .builder()
                            .setClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/$commandName Expand Force"))
                            .setHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    arrayOf(TextComponent(messages["Planet.Command.Expand.Chat.HoverText"]))
                                )
                            )
                            .build()
                    )
                    return
                } else if ("Force".isSameAsArg2()) player.hasPermissionBy("Planet.Command.Expand.Force") {
                    if (delay.contains(player)) {
                        player.sendConfigurableMessage("Planet.Command.Expand.Force.Delay")
                        return
                    } else {
                        if (
                            Holder.instance.playerData.getPlayer(owner)?.isOnHisPlanet() == true) {
                            delay.add(player)
                            planet.expand()
                            thread {
                                Thread.sleep(1000)
                                delay.remove(player)
                            }
                            player.sendMessage(messages["Planet.Command.Expand.Force"].replacePrice(planet))
                        } else player.sendConfigurableMessage("Planet.Command.Expand.Force.PlayerIsNotOnHisIsLand")
                        return
                    }
                }
            }
            "Shape".isSameAsArg1() && "Cube".isSameAsArg2() -> player.hasPermissionBy("Planet.Command.Style.Cube") {
                if (planet !is SpherePlanet) {
                    player.sendConfigurableMessage("Planet.Command.Shape.Cube.IsNotSpherePlanet")
                    return
                } else {
                    //TODO #83
                    player.sendMessage("Not implemented safely yet. Sorry :(")
//                    planet.toCubePlanet()
                    //TODO Add success message
                    return
                }
            }
        }
        sendUseMessage(player)
    }

    private fun args3(player: Player, arg1: String, arg2: String, arg3: String) {
        fun String.isSameAsArg2() = this.isSame(arg2)
        val planet = Planet.getPlanetWithMessageOnFail(Owner(player.uniqueId), player) ?: return
        try {
            @Suppress("DEPRECATION")
            if ("Style".isSame(arg1)) {
                val material: Material = try {
                    Material.getMaterial(arg3.toInt())
                } catch (ex: NumberFormatException) {
                    Material.getMaterial(arg3)
                }
                if (player.hasPermission(
                        permissions["Planet.Command.Style.UniqueID.$material"]
                            ?: permissions["Planet.Command.Style.UniqueID.<Material>"]
                                ?.replace("<Material>", material.toString(), true)
                    )
                ) {
                    planet.edit(blockDamage = material.id).fix()
                    return
                }
            } else if ("SubID".isSameAsArg2()) {
                val subID = arg3.toInt()
                val material = Material.getMaterial(planet.blockID)
                if (player.hasPermission(
                        permissions["Planet.Command.Style.SubID.$material.$subID"]
                            ?: permissions["Planet.Command.Style.SubID.<Material>.<UniqueID>"]
                                ?.replace("<Material>", material.toString(), true)
                                ?.replace("<UniqueID>", subID.toString(), true)
                    )
                ) {
                    planet.edit(blockID = subID).fix()
                    return
                }
            }
        } catch (ex: Exception) {
            player.sendMessage(
                messages["Planet.Command.Style.Arg3.Exception"]?.replace(
                    "<Exception>",
                    ex.message.toString(),
                    true
                )
            )
            return
        }
        sendUseMessage(player)
    }

    private inline fun CommandSender.hasPermissionBy(permissionKey: String, block: () -> Unit) =
        if (hasPermission(this, permissions[permissionKey] ?: "")) block() else sendMessage(permissionMessage)

    private fun String.isSame(check: String) = check.equals(this, true)

    private fun String?.replacePrice(planet: Planet) = this
        ?.replace("<Price>", planet.getPrice().toString(), true)
        ?.replace("<NextPrice>", Planet.getPrice(planet.size + 1.toDouble()).toString(), true)

}
