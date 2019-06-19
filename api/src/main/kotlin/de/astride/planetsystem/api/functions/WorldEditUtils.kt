package de.astride.planetsystem.api.functions

import com.boydti.fawe.FaweAPI
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet
import org.bukkit.Location
import org.bukkit.World

/*
 * Created on 15.02.2019 03:19.
 * @author Lars Artmann | LartyHD
 */

typealias BukkitVector = org.bukkit.util.Vector
typealias WEWorld = com.sk89q.worldedit.world.World
typealias WEVector = com.sk89q.worldedit.Vector

fun BukkitVector.toWEVector(): WEVector = WEVector(x, y, z)

fun Location.toWEVector(): WEVector = WEVector(x, y, z)
fun PlanetLocation.toWEVector(planet: LoadedPlanet): WEVector = toBukkitLocation(planet).toWEVector()

fun World.toWEWorld(): WEWorld = FaweAPI.getWorld(name)
fun Location.toWEWorld(): WEWorld = world.toWEWorld()