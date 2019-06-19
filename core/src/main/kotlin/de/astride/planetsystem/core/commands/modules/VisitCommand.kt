@file:Suppress("unused")

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.holder.find
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.databaseHandler
import de.astride.planetsystem.api.proxies.loadedPlanets
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.toPlanet
import de.astride.planetsystem.core.listeners.teleportPlanetSpawn
import de.astride.planetsystem.core.log.MessageKeys.COMMANDS_VISIT_FAILED_NO_ARGS
import de.astride.planetsystem.core.planet.BaseLoadedPlanet
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.jvmErasure

//TODO split in in a api
class VisitCommand : PlanetCommandModule {

    private val KFunction<*>.perm: String
        get() {
            val base = "${permissions(emptyArray())}.$name"
            val permission = findAnnotation<Permission>() ?: return base
            return "$base.${permission.value}"
        }

    override fun execute(planetPlayer: PlanetPlayer, args: Array<String>): Unit = if (args.size == 1)
        planetPlayer.execute(Owner(args[0].toPlayerUUID()))
    else planetPlayer.logger.warn(COMMANDS_VISIT_FAILED_NO_ARGS)

    private fun PlanetPlayer.execute(owner: Owner) {

        javaClass.kotlin.functions.forEach { function ->

            if (!player.hasPermission(function.perm)) return@forEach

            var parameters: Array<Any?>? = null
            when (function.parameters.singleOrNull()?.type?.jvmErasure) {
                LoadedPlanet::class -> {
                    val loadedPlanet = loadedPlanets.find(owner)
                    if (loadedPlanet != null)
                        parameters = arrayOf(loadedPlanet)
                    else logger.warn("planet.not.loaded")
                }
                BaseLoadedPlanet::class -> {
                    val databasePlanet = databaseHandler.findPlanet(owner)
                    if (databasePlanet != null)
                        parameters = arrayOf(databasePlanet)
                    else logger.warn("planet.not.exists")
                }
            }
            if (parameters != null) {
                function.call(this, *parameters)
                return
            }

        }
        logger.warn("no.perms")

    }

    @Permission("loaded")
    private fun PlanetPlayer.visit(loadedPlanet: LoadedPlanet): Unit = visit(loadedPlanet)

    @Permission("unloaded")
    private fun PlanetPlayer.visit(databasePlanet: DatabasePlanet): Unit = visit(databasePlanet.toPlanet())

    private fun PlanetPlayer.visit(planet: Planet) {
        planet.load { player.teleportPlanetSpawn(it) }
        logger.info("planet.visit.success")
    }

}

annotation class Permission(val value: String = "")