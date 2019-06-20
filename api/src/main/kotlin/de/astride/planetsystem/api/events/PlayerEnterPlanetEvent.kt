/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.events

import de.astride.planetsystem.api.able.LoadedPlanetable
import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Created on 20.06.2019 20:57.
 * @author Lars Artmann | LartyHD
 */
class PlayerEnterPlanetEvent(
    who: Player,
    override val planet: LoadedPlanet
) : PlayerEvent(who), LoadedPlanetable, KCancellable {

    /**
     * if is `true` tries to cancel the action
     * @author Lars Artmann | LartyHD
     */
    override var cancel: Boolean = false

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic //Important for Bukkit due to the Java ByteCode
        val handlerList: HandlerList = HandlerList()
    }

}
