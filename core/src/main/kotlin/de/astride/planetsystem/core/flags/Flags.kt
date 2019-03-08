package de.astride.planetsystem.core.flags

import org.bukkit.World
import org.bukkit.entity.EntityType

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

        val types = arrayOf(
            EntityType.WOLF,
            EntityType.PIG,
            EntityType.SHEEP,
            EntityType.COW,
            EntityType.CHICKEN,
            EntityType.SQUID,
            EntityType.BAT,
            EntityType.MUSHROOM_COW,
//            SNOWMAN,
            EntityType.OCELOT,
            EntityType.HORSE,
            EntityType.RABBIT,
            EntityType.VILLAGER
        )

    }

    object Mobs : Flags() {
        override var value: Boolean = true

        val types = arrayOf(
            EntityType.IRON_GOLEM,
            EntityType.CREEPER,
            EntityType.SKELETON,
            EntityType.SPIDER,
            EntityType.GIANT,
            EntityType.ZOMBIE,
            EntityType.SLIME,
            EntityType.GHAST,
            EntityType.PIG_ZOMBIE,
            EntityType.ENDERMAN,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH,
            EntityType.BLAZE,
            EntityType.MAGMA_CUBE,
            EntityType.ENDER_DRAGON,
            EntityType.WITHER,
            EntityType.WITCH,
            EntityType.ENDERMITE,
            EntityType.GUARDIAN
        )
    }

    object FireTick : Flags() {

        var world: World? = null

        override var value: Boolean = false
            set(value) {
                field = value
                world?.setGameRuleValue("doFireTick", value.toString())
            }

        init {
            value = false
        }

    }

    object PvP : Flags() {
        override var value: Boolean = false
    }

}