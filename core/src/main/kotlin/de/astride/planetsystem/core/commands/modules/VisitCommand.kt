/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

@file:Suppress("unused")

package de.astride.planetsystem.core.commands.modules

import de.astride.planetsystem.api.database.OfflinePlanet
import de.astride.planetsystem.api.database.allMembers
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.loadedPlanet
import de.astride.planetsystem.api.proxies.planet
import de.astride.planetsystem.core.commands.PlanetCommandModule
import de.astride.planetsystem.core.functions.load
import de.astride.planetsystem.core.listeners.teleportPlanetSpawn
import de.astride.planetsystem.core.log.MessageKeys.*
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

        VisitCommand::class.declaredFunctions.toSet().reversed().forEach { function ->
            function.findAnnotation<Permission>() ?: return@forEach
            if (!player.hasPermission(function.perm)) return@forEach

            var parameters: Array<Any?>? = null
            when (function.parameters.lastOrNull()?.type?.jvmErasure) {
                LoadedPlanet::class -> {
                    val loadedPlanet = owner.loadedPlanet
                    if (loadedPlanet != null)
                        parameters = arrayOf(loadedPlanet)
                    else logger.warn(COMMANDS_VISIT_NOT_LOADED)
                }
                OfflinePlanet::class -> {
                    val databasePlanet = owner.planet
                    if (databasePlanet != null)
                        parameters = arrayOf(databasePlanet)
                    else logger.warn(COMMANDS_VISIT_NOT_EXISTS)
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
    private fun PlanetPlayer.teleport(loadedPlanet: LoadedPlanet): Unit = teleport(loadedPlanet as OfflinePlanet)

    @Permission("unloaded")
    private fun PlanetPlayer.teleport(planet: OfflinePlanet) {

        val owner = Owner(player.uniqueId)
        if (planet.locked && owner !in planet.allMembers) {
            logger.warn(COMMANDS_VISIT_FAILED_PLANET_IS_LOCKED)
            return
        }

        val loadedPlanet = planet.load()
        if (player.teleportPlanetSpawn(loadedPlanet))
            logger.info(COMMANDS_VISIT_TELEPORT_SUCCESS)
        else logger.info(COMMANDS_VISIT_TELEPORT_FAILED)

    }

}

annotation class Permission(val value: String = "")