/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.functions

import com.boydti.fawe.FaweAPI
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.location.toBukkitLocation
import de.astride.planetsystem.api.planet.LoadedPlanet

/*
 * Created on 15.02.2019 03:19.
 * @author Lars Artmann | LartyHD
 */

fun Number.toBukkitVector(): BukkitVector = toDouble().run { BukkitVector(this, this, this) }
fun BukkitVector.toWEVector(): WEVector = WEVector(x, y, z)

fun BukkitLocation.toWEVector(): WEVector = WEVector(x, y, z)
fun PlanetLocation.toWEVector(planet: LoadedPlanet): WEVector = toBukkitLocation(planet).toWEVector()

fun BukkitWorld.toWEWorld(): WEWorld = FaweAPI.getWorld(name)
fun BukkitLocation.toWEWorld(): WEWorld = world.toWEWorld()