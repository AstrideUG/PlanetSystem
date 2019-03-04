package de.astride.planetsystem.core.flags

import de.astride.planetsystem.api.holder.Holder

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 06:03.
 * Current Version: 1.0 (04.03.2019 - 04.03.2019)
 */
sealed class Flags {

    abstract var value: Boolean

    init {
        FireTick //For the static init
    }

    object Animals : Flags() {
        override var value: Boolean = true

    }

    object Mobs : Flags() {
        override var value: Boolean = true
    }

    object FireTick : Flags() {
        override var value: Boolean = false
            set(value) {
                field = value
                Holder.instance.gridHandler.world.setGameRuleValue("doFireTick", value.toString())
            }

        init {
            value = false
        }

    }

    object PvP : Flags() {
        override var value: Boolean = false
    }

}