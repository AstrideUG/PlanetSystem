package de.astride.planetsystem.core.atmosphere

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.core.service.ConfigService
import xyz.morphia.annotations.Embedded

/**
 * Created by DevSnox on 12.02.18
 * Copyright (c) 2018 DevSnox
 * GitHub: https://github.com/DevSnox
 * Web: http://devsnox.me
 * Mail: me.devsnox@gmail.com
 * Discord: DevSnox#4884 | Skype: live:chaos3729
 */
@Embedded
data class DataAtmosphere(
    override val size: Byte = planets.defaultSize,
    override val maxSize: Byte = planets.maxSize,
    override val blockID: Int = planets.defaultBlockPattern.id,
    override val blockDamage: Int = planets.defaultBlockPattern.damage
) : Atmosphere {

    override fun edit(size: Byte, maxSize: Byte, blockID: Int, blockDamage: Int): Atmosphere =
        DataAtmosphere(size, maxSize, blockID, blockDamage)

    companion object {
        private val planets get() = ConfigService.instance.config.planets
    }

}

