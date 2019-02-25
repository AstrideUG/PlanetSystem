package de.astride.planetsystem.api.log

interface Logger {

    fun info(vararg any: Any) = log(Level.INFO, *any)
    fun warn(vararg any: Any) = log(Level.WARNING, *any)
    fun success(vararg any: Any) = log(Level.SUCCESSFULLY, *any)

    fun log(level: Level, vararg message: Any)

    enum class Level {
        INFO,
        WARNING,
        SUCCESSFULLY
    }

}
