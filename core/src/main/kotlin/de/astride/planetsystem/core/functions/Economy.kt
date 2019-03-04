/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.functions

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
 * Last edit 28.02.2019
 */

val economy get() = Bukkit.getServicesManager()?.getRegistration(Economy::class.java)?.provider.toNonNull("Vault Economy")

inline fun Player.removeIfHasEnough(price: Double, onSuccess: () -> Unit) = hasEnough(price) {
    economy.withdrawPlayer(this, price)
    onSuccess()
}

inline fun Player.hasEnough(price: Double, onSuccess: () -> Unit) = hasEnough(price, onSuccess) {
    sendConfigurableMessage("PlayerHasNotEnoughMoney")
}

inline fun OfflinePlayer.hasEnough(price: Double, onSuccess: () -> Unit, onFail: () -> Unit) =
    if (economy.has(this, price)) onSuccess() else onFail()