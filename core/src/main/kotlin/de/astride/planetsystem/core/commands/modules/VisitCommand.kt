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
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayerUUID
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible
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

        VisitCommand::class.declaredFunctions.forEach { function ->
            function.findAnnotation<Permission>() ?: return@forEach
            if (!player.hasPermission(function.perm)) return@forEach

            var parameters: Array<Any?>? = null
            when (function.parameters.lastOrNull()?.type?.jvmErasure) {
                LoadedPlanet::class -> {
                    val loadedPlanet = loadedPlanets.find(owner)
                    if (loadedPlanet != null)
                        parameters = arrayOf(loadedPlanet)
                    else logger.warn("planet.not.loaded")
                }
                DatabasePlanet::class -> {
                    val databasePlanet = databaseHandler.findPlanet(owner)
                    if (databasePlanet != null)
                        parameters = arrayOf(databasePlanet)
                    else logger.warn("planet.not.exists")
                }
            }

            if (parameters != null) {
                function.isAccessible = true
                function.call(this@VisitCommand, this, *parameters)
                return
            }

        }
        logger.warn("no.perms")

    }

    @Permission("loaded")
    private fun PlanetPlayer.teleport(loadedPlanet: LoadedPlanet): Unit = teleport(loadedPlanet)

    @Permission("unloaded")
    private fun PlanetPlayer.teleport(databasePlanet: DatabasePlanet): Unit = teleport(databasePlanet.toPlanet())

    private fun PlanetPlayer.teleport(planet: Planet) {
        planet.load { player.teleportPlanetSpawn(it) }
        logger.info("planet.visit.teleport.success")
    }

}

annotation class Permission(val value: String = "")