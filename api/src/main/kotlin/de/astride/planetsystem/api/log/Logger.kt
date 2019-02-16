package de.astride.planetsystem.api.log

interface Logger {

	fun info(vararg `object`: Any) = log(Level.INFO, *`object`)
	fun warn(vararg `object`: Any) = log(Level.WARNING, *`object`)
	fun success(vararg `object`: Any) = log(Level.SUCCESSFULLY, *`object`)

	fun log(level: Level, vararg message: Any)

	enum class Level {
		INFO,
		WARNING,
		SUCCESSFULLY
	}

}
