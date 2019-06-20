/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.events

import org.bukkit.event.Cancellable

/**
 * Created on 20.06.2019 21:02.
 * @author Lars Artmann | LartyHD
 */
interface KCancellable : Cancellable {

    var cancel: Boolean

    override fun isCancelled(): Boolean = cancel

    override fun setCancelled(value: Boolean) {
        cancel = value
    }

}