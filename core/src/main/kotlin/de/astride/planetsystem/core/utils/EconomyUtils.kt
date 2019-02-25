/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.utils

import net.darkdevelopers.darkbedrock.darkness.general.functions.toNonNull
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 22.09.2018 19:44.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 24.02.2019
 */
object EconomyUtils {

    val economy
        get() = Bukkit.getServicesManager()?.getRegistration(Economy::class.java)?.provider.toNonNull("Vault Economy")

    fun removeWhenHasEnough(player: Player, price: Double, onSuccess: () -> Unit) =
        hasEnough(player, price) {
            economy.withdrawPlayer(player, price)
            onSuccess()
        }

    fun hasEnough(player: Player, price: Double, onSuccess: () -> Unit) =
        hasEnough(
            player,
            price,
            onSuccess
        ) { player.sendConfigurableMessage("PlayerHasNotEnoughMoney") }

    fun hasEnough(player: OfflinePlayer, price: Double, onSuccess: () -> Unit, onFail: () -> Unit) =
        if (economy.has(player, price)) onSuccess() else onFail()

}