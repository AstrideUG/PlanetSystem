/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.holder

import de.astride.planetsystem.api.handler.DatabaseHandler
import de.astride.planetsystem.api.handler.GridHandler
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer

/*
 * Created on 20.06.2019 00:16.
 * @author Lars Artmann | LartyHD
 */

lateinit var databaseHandler: DatabaseHandler
lateinit var gridHandler: GridHandler

val players: MutableSet<PlanetPlayer> = mutableSetOf()
val loadedPlanets: MutableSet<LoadedPlanet> = mutableSetOf()