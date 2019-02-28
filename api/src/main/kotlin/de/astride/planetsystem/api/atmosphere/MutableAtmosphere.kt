package de.astride.planetsystem.api.atmosphere

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 20:04.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
interface MutableAtmosphere : Atmosphere {

    override var size: Byte
    override var maxSize: Byte
    override var blockID: Int
    override var blockDamage: Int

}