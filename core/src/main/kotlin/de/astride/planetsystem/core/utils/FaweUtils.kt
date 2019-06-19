/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */


@file:Suppress("DEPRECATION")

package de.astride.planetsystem.core.utils

import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.patterns.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import org.bukkit.Location
import org.bukkit.World

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 31.08.2018 18:36.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 15.12.2018
 */
object FaweUtils {

    fun setHSphere(location: Location, radius: Double, pattern: Pattern) {
        val editSession = EditSessionBuilder(location.world.name).allowedRegionsEverywhere().limitUnlimited().build()
        editSession.makeSphere(Vector(location.x, location.y, location.z), pattern, radius, false)
        editSession.flushQueue()
    }

    fun setCuboid(location: Location, radius: Double, pattern: Pattern) {
        fun Int.plus() = this + radius.toInt()
        fun Int.minus() = this - radius.toInt()
        val x1 = location.blockX.plus()
        val y1 = location.blockY.plus()
        val z1 = location.blockZ.plus()
        val x2 = location.blockX.minus()
        val y2 = location.blockY.minus()
        val z2 = location.blockZ.minus()
        setCuboid(location.world, Vector(x1, y1, z1), Vector(x2, y2, z2), pattern)
    }

    fun setCuboid(world: World, vector1: Vector, vector2: Vector, pattern: Pattern) {
        println(vector1)
        println(vector2)
        val editSession = EditSessionBuilder(world.name).limitUnlimited().build()
        editSession.makeCuboidFaces(CuboidRegion(vector1, vector2), pattern)
        editSession.flushQueue()
    }

}