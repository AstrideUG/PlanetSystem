/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.service

import com.google.gson.JsonObject
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.general.functions.toNonNull
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages
import org.bukkit.Bukkit
import java.io.File


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.08.2018 19:50.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 18.03.2019
 */
class ConfigService(var directory: File) {

    companion object {
        val instance: ConfigService
            get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider.toNonNull("ConfigService")
    }

    /* SubClass */
    val config by lazy { Config() }
    val flags by lazy { Flags() }

    inner class Config internal constructor() {

        /* Main */
        private val configData by lazy { ConfigData(directory, "planets.json") }
        private val config by lazy { @Suppress("DEPRECATION") GsonConfig(configData).load() }
        private val jsonObject get() = config.jsonObject
        /* Values */
        val flagsFileName by lazy { jsonObject["FlagsFileName"]?.asString ?: "flags.json" }
        val planetCommand by lazy { jsonObject["PlanetCommand"]?.asString ?: "Planet" }
        val databaseHost by lazy { jsonObject["database-host"]?.asString ?: "127.0.0.1" }
        val databasePort by lazy { jsonObject["database-port"]?.asInt ?: 27017 }
        val planetCommandAliases by lazy {
            jsonObject["PlanetCommandAliases"]?.asJsonArray?.map { it.asString }?.toTypedArray() ?: arrayOf("p")
        }
        val spigotGsonMessages by lazy { SpigotGsonMessages(config) }
        //        val permissions by lazy { GsonStringMapWithSubs(jsonObject["permissions"]?.asJsonObject ?: return@lazy null) }
        /* SubClass */
        val planets by lazy { Planets(jsonObject[Planets::class.java.simpleName]?.asJsonObject) }

        val gridMaxSize: Int by lazy { jsonObject["GridMaxSize"]?.asInt ?: 2048 }
        val gameWorld: String by lazy { jsonObject["GameWorld"]?.asString ?: "PlanetWorld" }

        inner class Planets internal constructor(jsonObject: JsonObject?) {

            /* Values */
            val defaultSize = jsonObject?.get("DefaultSize")?.asByte ?: 8
            val maxSize = jsonObject?.get("MaxSize")?.asByte ?: 126
            val pow = jsonObject?.get("Pow")?.asDouble ?: 3.0
            val doNotThrowIllegalStateExceptionBySizeCheck =
                jsonObject?.get("DoNotThrowIllegalStateExceptionBySizeCheck")?.asBoolean
                    ?: false
            /* SubClass */
            val defaultBlockPattern by lazy { DefaultBlockPattern(jsonObject?.get(DefaultBlockPattern::class.java.simpleName)?.asJsonObject) }

            inner class DefaultBlockPattern internal constructor(jsonObject: JsonObject?) {
                val id = jsonObject?.get("ID")?.asInt ?: 95
                val damage = jsonObject?.get("Damage")?.asInt ?: 7
            }

        }

    }

    inner class Flags internal constructor() {

        private val configData = ConfigData(directory, config.flagsFileName)
        private val jsonObject = GsonService.load(configData) as? JsonObject

        /* SubClass */
        val animals by lazy { Animals(jsonObject?.get(Animals::class.simpleName)?.asJsonObject) }
        val mobs by lazy { Mobs(jsonObject?.get(Mobs::class.simpleName)?.asJsonObject) }
        val fireTick by lazy { FireTick(jsonObject?.get(FireTick::class.simpleName)?.asJsonObject) }
        val pvp by lazy { PvP(jsonObject?.get(PvP::class.simpleName)?.asJsonObject) }

        inner class Animals internal constructor(jsonObject: JsonObject?) {

            val value = jsonObject?.get("value")?.asBoolean ?: true
            val types = jsonObject?.get("types")?.asJsonArray

        }

        inner class Mobs internal constructor(jsonObject: JsonObject?) {

            val value = jsonObject?.get("value")?.asBoolean ?: true
            val types = jsonObject?.get("types")?.asJsonArray

        }

        inner class FireTick internal constructor(jsonObject: JsonObject?) {

            val value = jsonObject?.get("value")?.asBoolean ?: false
//            val world: World? =
//                Bukkit.getWorld(jsonObject?.get("world")?.asString ?: Holder.instance.gridHandler.world.name)

        }

        inner class PvP internal constructor(jsonObject: JsonObject?) {

            val value = jsonObject?.get("value")?.asBoolean ?: false

        }

    }

}