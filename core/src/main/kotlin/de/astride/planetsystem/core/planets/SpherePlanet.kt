/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.planets

import com.sk89q.worldedit.function.pattern.BlockPattern
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.core.utils.FaweUtils
import org.bukkit.Location

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 20.10.2018 16:16.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 24.02.2019
 */
class SpherePlanet(
    owner: Owner,
    center: Location,
    size: Byte = config.defaultSize,
    maxSize: Byte = config.maxSize,
    blockDamage: Int = config.defaultBlockPattern.damage,
    blockID: Int = config.defaultBlockPattern.id
) : Planet(owner, center, size, maxSize, blockDamage, blockID) {

    fun toCubePlanet(): CubePlanet = CubePlanet(owner, center, size, maxSize, blockDamage, blockID).let {
        delete(size.toDouble())
        it.fix()
        edit(it)
    }

    override fun use(size: Double, @Suppress("DEPRECATION") blockPattern: BlockPattern): Unit =
        FaweUtils.setHSphere(center, size, blockPattern)

    override fun copy(
        owner: Owner,
        center: Location,
        size: Byte,
        maxSize: Byte,
        blockDamage: Int,
        blockID: Int
    ) =
        SpherePlanet(owner, center, size, maxSize, blockDamage, blockID)

}