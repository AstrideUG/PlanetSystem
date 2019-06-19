/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.service

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import de.astride.planetsystem.core.functions.toMaterial
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.general.functions.toNonNull
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
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

    /* SubClass */
    val config by lazy { Config() }
    val flags by lazy { Flags() }

    inner class Config internal constructor() {

        /* Main */
        private val configData by lazy { ConfigData(directory, "${Config::class.simpleName?.toLowerCase()}.json") }
        private val config by lazy { @Suppress("DEPRECATION") GsonConfig(configData).load() }
        private val jsonObject get() = config.jsonObject
        /* Values */
        val flagsFileName by lazy { jsonObject["FlagsFileName"]?.asString ?: "flags.json" }
        val planetCommand by lazy { jsonObject["PlanetCommand"]?.asString ?: "Planet" }
        val planetCommandAliases by lazy {
            jsonObject["PlanetCommandAliases"]?.asJsonArray?.map { it.asString }?.toTypedArray() ?: arrayOf("p")
        }
        val spigotGsonMessages by lazy { SpigotGsonMessages(config) }
        //        val permissions by lazy { GsonStringMapWithSubs(jsonObject["permissions"]?.asJsonObject ?: return@lazy null) }
        /* SubClass */
        val planets by lazy { Planets(jsonObject[Planets::class.java.simpleName]?.asJsonObject) }
        val commands by lazy { Commands(jsonObject[Commands::class.java.simpleName]?.asJsonObject) }

        inner class Commands internal constructor(jsonObject: JsonObject?) {

            /* SubClass */
            val restart by lazy { Restart(jsonObject?.get(Restart::class.java.simpleName)?.asJsonObject) }

            //TODO: Add to Darkness-Spigot
            inner class Restart internal constructor(jsonObject: JsonObject?) {

                private val size: Int? = jsonObject?.get("size")?.asInt
                private val type: InventoryType =
                    InventoryType.valueOf(jsonObject?.get("type")?.asString?.toUpperCase() ?: "CHEST")
                private val name = jsonObject?.get("name")?.asString ?: type.defaultTitle

                private val contents = jsonObject?.get("contents")?.asJsonArray?.map { element: JsonElement? ->

                    if (element !is JsonObject) return@map null

                    val material = element.get("material")?.asString?.toMaterial() ?: return@map null
                    val amount = element.get("amount")?.asInt ?: 1
                    val damage = element.get("damage")?.asShort ?: 0

                    val name = element.get("name")?.asString
                    val lore = element.get("lore")?.asJsonArray?.mapNotNull { it.asString } ?: emptyList()

                    val owner = element.get("owner")?.asJsonObject
                    val ownerName = owner?.get("name")?.asString
                    val ownerURL = owner?.get("url")?.asString

                    val builder = ItemBuilder(material, amount, damage).setLore(lore) //TODO: add more
                    if (name != null) builder.setName(name)
                    if (ownerName != null)
                        if (ownerURL != null) builder.setOwner(ownerURL, ownerName)
                        else builder.setOwner(ownerName)

                    return@map builder.build()

                }?.toTypedArray() ?: emptyArray()

                private val inventoryBuilder =
                    if (size == null) InventoryBuilder(type, name) else InventoryBuilder(size, name)
                val inventory = inventoryBuilder.build().apply { contents = this@Restart.contents }

            }

        }


        val gridMaxSize: Int by lazy { jsonObject["GridMaxSize"]?.asInt ?: 2048 }
        val gameWorld: String by lazy { jsonObject["GameWorld"]?.asString ?: "PlanetWorld" }

        inner class Planets internal constructor(jsonObject: JsonObject?) {

            /* Values */
            val defaultSize = jsonObject?.get("DefaultSize")?.asByte ?: 8
            val maxSize = jsonObject?.get("MaxSize")?.asByte ?: 126
            val pow = jsonObject?.get("Pow")?.asDouble ?: 3.0
            val doNotThrowIllegalStateExceptionBySizeCheck =
                jsonObject?.get("DoNotThrowIllegalStateExceptionBySizeCheck")?.asBoolean ?: false
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

    companion object {
        val instance: ConfigService
            get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider.toNonNull("ConfigService")
    }

}