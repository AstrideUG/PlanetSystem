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
 * Created by Lars Artmann | LartyHD on 20.10.2018 16:24.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 08.01.2019
 */
class CubePlanet(
    owner: Owner,
    center: Location,
    size: Byte = config.defaultSize,
    maxSize: Byte = config.maxSize,
    blockDamage: Int = config.defaultBlockPattern.damage,
    blockID: Int = config.defaultBlockPattern.id
) : Planet(owner, center, size, maxSize, blockDamage, blockID) {

    override fun use(size: Double, @Suppress("DEPRECATION") blockPattern: BlockPattern): Unit =
        FaweUtils.setCuboid(center, size, blockPattern)

    override fun copy(
        owner: Owner,
        center: Location,
        size: Byte,
        maxSize: Byte,
        blockDamage: Int,
        blockID: Int
    ) = CubePlanet(owner, center, size, maxSize, blockDamage, blockID)

}