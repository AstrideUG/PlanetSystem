/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.service

import com.google.gson.JsonObject
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonStringMapWithSubs
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
 * Last edit 24.02.2019
 */
internal class ConfigService(var directory: File) {

    companion object {
        val instance: ConfigService
            get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider.toNonNull("ConfigService")
    }

    /* SubClass */
    val config by lazy { Config() }
//    val data by lazy { Data() }

    inner class Config internal constructor() {

        /* Main */
        private val configData by lazy { ConfigData(directory, "planets.json") }
        private val config by lazy { @Suppress("DEPRECATION") GsonConfig(configData).load() }
        private val jsonObject get() = config.jsonObject
        /* Values */
//        val dataFileName by lazy { jsonObject["DataFileName"]?.asString ?: "data.json" }
        val planetCommand by lazy { jsonObject["PlanetCommand"]?.asString ?: "Planet" }
        val spigotGsonMessages by lazy { SpigotGsonMessages(config) }
        val permissions by lazy { GsonStringMapWithSubs(jsonObject["permissions"]?.asJsonObject ?: return@lazy null) }
        /* SubClass */
        val planets by lazy { Planets(jsonObject[Planets::class.java.simpleName]?.asJsonObject) }

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
            val addToCenter by lazy { AddToCenter(jsonObject?.get(AddToCenter::class.java.simpleName)?.asJsonObject) }

            inner class DefaultBlockPattern internal constructor(jsonObject: JsonObject?) {
                val id = jsonObject?.get("ID")?.asInt ?: 95
                val damage = jsonObject?.get("Damage")?.asInt ?: 7
            }

            inner class AddToCenter internal constructor(jsonObject: JsonObject?) {
                val x = jsonObject?.get("X")?.asDouble ?: 0.0
                val y = jsonObject?.get("Y")?.asDouble ?: 0.0
                val z = jsonObject?.get("Z")?.asDouble ?: 0.0
            }

        }

    }

//    inner class Data internal constructor() {
//
//        /* Main */
//        private val configData by lazy { ConfigData(directory, this@ConfigService.planets.dataFileName) }
//        private val planets by lazy { @Suppress("DEPRECATION") GsonConfig(configData).load() }
//        private val jsonObject: JsonObject get() = planets.jsonObject
//        /* Values */
//        private val globalWorld = jsonObject.get("world")?.asJsonPrimitive?.asString
//        /* SubClass */
//        val planets by lazy { Planets(jsonObject["Planets"]?.asJsonArray ?: JsonArray()) }
//
//        inner class Planets internal constructor(private val jsonArray: JsonArray) {
//
//            fun load(): Set<Planet> = jsonArray.mapNotNullTo(HashSet()) {
//                try {
//                    val entry = it.asJsonObject
//                    val owner = UUID.fromString(entry.get("owner").asJsonPrimitive.asString)
//                    val center = entry.get("center").asJsonObject.run {
//
//                        val world = this.get("world")?.asJsonPrimitive?.asString ?: globalWorld
//                        val x = this.get("x").asJsonPrimitive.asDouble
//                        val y = this.get("y").asJsonPrimitive.asDouble
//                        val z = this.get("z").asJsonPrimitive.asDouble
//
//                        Location(Bukkit.getWorld(world), x, y, z)
//                    }
//                    val defaults = instance.planets.planets
//                    val size = entry.get("size")?.asJsonPrimitive?.asByte ?: defaults.defaultSize
//                    val maxSize = entry.get("maxSize")?.asJsonPrimitive?.asByte ?: defaults.maxSize
//                    val blockDamage = entry.get("blockDamage")?.asJsonPrimitive?.asInt
//                        ?: defaults.defaultBlockPattern.damage
//                    val blockID = entry.get("blockID")?.asJsonPrimitive?.asInt ?: defaults.defaultBlockPattern.id
//                    val planet = when (val asString = entry.get("type")?.asJsonPrimitive?.asString) {
//                        null, "SpherePlanet" -> SpherePlanet(owner, center, size, maxSize, blockDamage, blockID)
//                        "CubePlanet" -> CubePlanet(owner, center, size, maxSize, blockDamage, blockID)
//                        else -> throw IllegalStateException("Can's find a Type of $asString")
//                    }
//                    planet
//                } catch (ex: Exception) {
//                    ex.printStackTrace()
//                    null
//                }
//            }
//
//
//            fun save(planets: Set<Planet>) {
//                if (globalWorld == null) jsonObject.addProperty(
//                    "world", planets.firstOrNull()?.center?.world?.name
//                        ?: return
//                )
//                jsonObject.add("Planets", JsonArray().apply {
//                    planets.forEach { planet ->
//                        try {
//                            this@apply.add(JsonObject().apply {
//                                if (planet !is SpherePlanet) addProperty("type", planet.javaClass.simpleName)
//                                addProperty("owner", planet.owner.toString())
//                                add("center", JsonObject().apply {
//                                    val location = planet.rawCenter
//                                    if (globalWorld == null || globalWorld != location.world.name)
//                                        addProperty("world", location.world.name)
//                                    addProperty("x", location.x)
//                                    addProperty("y", location.y)
//                                    addProperty("z", location.z)
//                                })
//                                val defaults = instance.planets.planets
//                                val addToCenter = JsonObject().apply {
//                                    val addToCenter = defaults.addToCenter
//                                    if (addToCenter.x != 0.0) addProperty("x", addToCenter.x)
//                                    if (addToCenter.y != 0.0) addProperty("y", addToCenter.y)
//                                    if (addToCenter.z != 0.0) addProperty("z", addToCenter.z)
//                                }
//                                if (addToCenter.entrySet().isNotEmpty())
//                                    add("addToCenter", addToCenter)
//
//                                if (defaults.defaultSize != planet.size)
//                                    addProperty("size", planet.size)
//
//                                if (defaults.maxSize != planet.maxSize)
//                                    addProperty("maxSize", planet.maxSize)
//
//                                if (defaults.defaultBlockPattern.damage != planet.blockDamage)
//                                    addProperty("blockDamage", planet.blockDamage)
//
//                                if (defaults.defaultBlockPattern.id != planet.blockID)
//                                    addProperty("blockID", planet.blockID)
//                            })
//                        } catch (ex: Exception) {
//                            ex.printStackTrace()
//                        }
//                    }
//                })
//                GsonService.save(planets.configData, planets.jsonObject)
//            }
//        }
//}

}
