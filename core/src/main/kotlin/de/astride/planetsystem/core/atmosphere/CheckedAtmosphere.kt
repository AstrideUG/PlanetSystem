package de.astride.planetsystem.core.atmosphere

import de.astride.planetsystem.api.atmosphere.Atmosphere
import de.astride.planetsystem.api.atmosphere.MutableAtmosphere
import de.astride.planetsystem.core.proxies.planets

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 12:09.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
class CheckedAtmosphere(
    size: Byte = planets.defaultSize,
    maxSize: Byte = planets.maxSize,
    override var blockDamage: Int = planets.defaultBlockPattern.damage,
    override var blockID: Int = planets.defaultBlockPattern.id
) : MutableAtmosphere {

    constructor(
        atmosphere: Atmosphere,
        size: Byte? = null,
        maxSize: Byte? = null,
        blockDamage: Int? = null,
        blockID: Int? = null
    ) : this(
        size ?: atmosphere.size,
        maxSize ?: atmosphere.maxSize,
        blockDamage ?: atmosphere.blockDamage,
        blockID ?: atmosphere.blockID
    )

    override var size = SizeChecker.checkSize(size, maxSize)
        set(value) {
            field = SizeChecker.checkSize(value, maxSize)
        }

    override var maxSize = SizeChecker.checkSize(maxSize, maxSize)
        set(value) {
            field = SizeChecker.checkSize(value, maxSize)
        }

    override fun copy(size: Byte, maxSize: Byte, blockID: Int, blockDamage: Int): CheckedAtmosphere =
        CheckedAtmosphere(size, maxSize, blockDamage, blockID)

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
            if (!planets.doNotThrowIllegalStateExceptionBySizeCheck) throw IllegalStateException("Size $message") else check
        else size
    }

}

