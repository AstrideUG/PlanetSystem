package me.devsnox.planetsystem.api.log

interface KeyLogger : Logger {

	fun getValue(key: Any): Any

}
