package de.astride.planetsystem.core.atmosphere

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.core.service.ConfigService

/**
 * Created by DevSnox on 12.02.18
 * Copyright (c) 2018 DevSnox
 * GitHub: https://github.com/DevSnox
 * Web: http://devsnox.me
 * Mail: me.devsnox@gmail.com
 * Discord: DevSnox#4884 | Skype: live:chaos3729
 */

object SizeChecker {
    private const val MIN_SIZE: Byte = 2
    private const val MAX_SIZE: Byte = 126

    internal fun checkSize(size: Byte, maxSize: Byte): Byte = when {
        checkMax(size, maxSize) != size -> checkMax(size, maxSize)
        checkMin(size) != size -> checkMin(size)
        else -> size
    }

    private fun checkMin(size: Byte) =
        checkSize(size, MIN_SIZE, "lower than MIN_SIZE($MIN_SIZE)") { size < MIN_SIZE }

    private fun checkMax(size: Byte, maxSize: Byte) =
        checkSize(size, maxSize, "higher than maxSize($maxSize) (MAX_SIZE($MAX_SIZE))") { size > maxSize }

    private fun checkSize(size: Byte, check: Byte, message: String, algorithm: () -> Boolean) = if (algorithm())
        if (!ConfigService.instance.config.planets.doNotThrowIllegalStateExceptionBySizeCheck) throw IllegalStateException(
            "Size $message"
        ) else check
    else size

}

fun Atmosphere.checkedSize(): Atmosphere = edit(
    size = SizeChecker.checkSize(size, maxSize),
    maxSize = SizeChecker.checkSize(maxSize, maxSize)
)