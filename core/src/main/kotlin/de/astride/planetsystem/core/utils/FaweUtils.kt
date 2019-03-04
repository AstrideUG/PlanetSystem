/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.utils

import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.patterns.Pattern
import com.sk89q.worldedit.regions.CuboidRegion
import org.bukkit.Location

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
        val editSession = EditSessionBuilder(location.world.name).limitUnlimited().build()
        editSession.makeSphere(Vector(location.x, location.y, location.z), pattern, radius, false)
        editSession.flushQueue()
    }

    fun setCuboid(location: Location, radius: Double, pattern: Pattern) {
        val editSession = EditSessionBuilder(location.world.name).limitUnlimited().build()
        fun clonedLocation() = location.clone()
        fun Int.plus() = this + radius.toInt()
        fun Int.minus() = this - radius.toInt()
        val x1 = clonedLocation().blockX.plus()
        val y1 = clonedLocation().blockY.plus()
        val z1 = clonedLocation().blockZ.plus()
        val x2 = clonedLocation().blockX.minus()
        val y2 = clonedLocation().blockY.minus()
        val z2 = clonedLocation().blockZ.minus()
        editSession.makeCuboidFaces(CuboidRegion(Vector(x1, y1, z1), Vector(x2, y2, z2)), pattern)
        editSession.flushQueue()
    }

}