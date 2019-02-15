package me.devsnox.planetsystem.api.database

import java.util.*

interface DatabasePlayer {

	val uuid: UUID
	val planetUniqueId: UUID
	val memberedPlanets: List<UUID>

}
