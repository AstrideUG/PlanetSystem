package de.astride.planetsystem.api.functions

import com.boydti.fawe.FaweAPI
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet

/*
 * Created on 15.02.2019 03:19.
 * @author Lars Artmann | LartyHD
 */

typealias BukkitLocation = org.bukkit.Location
typealias BukkitWorld = org.bukkit.World
typealias BukkitVector = org.bukkit.util.Vector
typealias WEWorld = com.sk89q.worldedit.world.World
typealias WEVector = com.sk89q.worldedit.Vector

fun Number.toBukkitVector(): BukkitVector = toDouble().run { BukkitVector(this, this, this) }
fun BukkitVector.toWEVector(): WEVector = WEVector(x, y, z)

fun BukkitLocation.toWEVector(): WEVector = WEVector(x, y, z)
fun PlanetLocation.toWEVector(planet: LoadedPlanet): WEVector = toBukkitLocation(planet).toWEVector()

fun BukkitWorld.toWEWorld(): WEWorld = FaweAPI.getWorld(name)
fun BukkitLocation.toWEWorld(): WEWorld = world.toWEWorld()