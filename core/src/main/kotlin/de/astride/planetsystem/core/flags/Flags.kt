package de.astride.planetsystem.core.flags

import com.google.gson.JsonArray
import de.astride.planetsystem.core.service.ConfigService
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.EntityType.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.03.2019 06:03.
 * Current Version: 1.0 (04.03.2019 - 12.03.2019)
 */
sealed class Flags {

    protected val flags get() = ConfigService.instance.flags

    abstract var value: Boolean

    init {
        FireTick //For the static init
    }

    object Animals : Flags() {

        override var value: Boolean = flags.animals.value

        val types = flags.animals.types.convertTo(
            WOLF,
            PIG,
            SHEEP,
            COW,
            CHICKEN,
            SQUID,
            BAT,
            MUSHROOM_COW,/* SNOWMAN, */
            OCELOT,
            HORSE,
            RABBIT,
            VILLAGER
        )

    }

    object Mobs : Flags() {

        override var value: Boolean = flags.mobs.value

        val types = flags.mobs.types.convertTo(
            IRON_GOLEM,
            CREEPER,
            SKELETON,
            SPIDER,
            GIANT,
            ZOMBIE,
            SLIME,
            GHAST,
            PIG_ZOMBIE,
            ENDERMAN,
            CAVE_SPIDER,
            SILVERFISH,
            BLAZE,
            MAGMA_CUBE,
            ENDER_DRAGON,
            WITHER,
            WITCH,
            ENDERMITE,
            GUARDIAN
        )

    }

    object FireTick : Flags() {

        var world: World? = flags.fireTick.world

        override var value: Boolean = false
            set(value) {
                field = value
                world?.setGameRuleValue("doFireTick", value.toString())
            }

        init {
            value = flags.fireTick.value
        }

    }

    object PvP : Flags() {
        override var value: Boolean = flags.pvp.value
    }

}


private fun JsonArray?.convertTo(vararg default: EntityType) =
    this?.map { valueOf(it.asString.toUpperCase()) }?.toSet() ?: setOf(*default)