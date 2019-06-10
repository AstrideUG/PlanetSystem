package de.astride.planetsystem.api.atmosphere

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 11:26.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
interface Atmosphere {

    /**
     * [size] is the radius
     * Change size
     * and if loaded inner region and [atmosphere]
     */
    val size: Byte
    /**
     * [maxSize] is the radius
     */
    val maxSize: Byte
    val blockID: Int
    val blockDamage: Int

    fun edit(
        size: Byte = this.size,
        maxSize: Byte = this.maxSize,
        blockID: Int = this.blockID,
        blockDamage: Int = this.blockDamage
    ): Atmosphere
}
