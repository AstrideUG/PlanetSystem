package de.astride.planetsystem.api.functions

import com.boydti.fawe.FaweAPI
import com.sk89q.worldedit.Vector
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.toBukkitLocation
import org.bukkit.Location
import org.bukkit.World

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 15.02.2019 03:19.
 * Current Version: 1.0 (15.02.2019 - 15.02.2019)
 */


fun org.bukkit.util.Vector.toWEVector(): Vector = Vector(x, y, z)
fun Location.toWEVector(): Vector = Vector(x, y, z)
fun PlanetLocation.toWEVector(): Vector? = toBukkitLocation()?.toWEVector()

fun World.toWEWorld() = FaweAPI.getWorld(name)
fun Location.toWEWorld() = world.toWEWorld()