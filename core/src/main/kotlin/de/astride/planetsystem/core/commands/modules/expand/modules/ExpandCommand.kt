package de.astride.planetsystem.core.commands.modules.expand.modules

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.player.isOnHisPlanet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.commands.modules.expand.sendUsage
import de.astride.planetsystem.core.functions.*
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.universal.builder.textcomponent.builder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import kotlin.concurrent.thread

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 28.02.2019 08:48.
 * Current Version: 1.0 (28.02.2019 - 28.02.2019)
 */
class ExpandCommand : PlanetCommandModule {

    private val delay = mutableSetOf<Owner>()

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>) {
        val player = planetPlayer.player
        if (args.isEmpty()) {

            val title = messages["Planet.Command.Expand.Inventory.Name"]
                ?: messages["Planet.Command.NoArgs.Inventory.Entry.Expand"]
                ?: "Error"

            val name = messages["Planet.Command.Expand.Inventory.Entry.Expand"]
                ?: messages["Planet.Command.NoArgs.Inventory.Entry.Expand"]
                ?: "Error"

            val planet = findPlanetOrMessage(Owner(player.uniqueId), player) ?: return
            val price = planet.atmosphere.price

            val color = if (economy.has(player, price)) Colors.PRIMARY else ChatColor.RED
            val lore = "${Colors.TEXT}Preis${Colors.IMPORTANT}: $color$price"

            val item = ItemBuilder(Material.FIREWORK_CHARGE)
                .addAllItemFlags()
                .setName(name)
                .setFireworkEffectMetaEffect(FireworkEffect.builder().withColor(Color.RED).build())
                .addLore(lore)
                .build()

            player.openInventory(
                InventoryBuilder(player, InventoryType.DISPENSER, title)
                    .setDesign()
                    .setItem(4, item)
                    .build()
            )

        } else if (args.size == 1) {

            val owner = Owner(player.uniqueId)

            if (args.firstOrNull().equals("Chat", true) /*TODO: ADD PERMS*/) {
                player.spigot().sendMessage(
                    TextComponent(messages["Planet.Command.Expand.Chat"]?.replacePrice(planetPlayer.planet))
                        .builder()
                        .setClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/Planet Expand Expand Force"))
                        .setHoverEvent(
                            HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                arrayOf(TextComponent(messages["Planet.Command.Expand.Chat.HoverText"]))
                            )
                        )
                        .build()
                )
            } else if (args.firstOrNull().equals("Force", true) /*TODO: ADD PERMS*/) when {
                owner in delay -> player.sendConfigurableMessage("Planet.Command.Expand.Force.Delay")
                planetPlayer.isOnHisPlanet() -> {
                    delay += owner
                    expand(planetPlayer)
                    thread {
                        Thread.sleep(1000)
                        delay -= owner
                    }
                    player.sendMessage(messages["Planet.Command.Expand.Force"].replacePrice(planetPlayer.planet))
                }
                else -> player.sendConfigurableMessage("Planet.Command.Expand.Force.PlayerIsNotOnHisIsLand")
            }

        } else planetPlayer.player.sendUsage()

    }

    private fun expand(planetPlayer: PlanetPlayer) {
        val loadedPlanet = planetPlayer.planet
        loadedPlanet.atmosphere.apply {
            if (size >= maxSize) planetPlayer.player.sendConfigurableMessage("ExpandToBiggerSizeThanMaxSize")//TODO: CONVERT
            else planetPlayer.player.removeIfHasEnough(price) {
                loadedPlanet.delete()
                loadedPlanet.atmosphere = toMutable().apply { size++ }
                loadedPlanet.place()
            }
        }
    }

}