/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.log

import org.bukkit.entity.Player

interface PlayerLogger : Logger {
    val player: Player
}
