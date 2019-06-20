/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.able

import de.astride.planetsystem.api.planet.LoadedPlanet

/**
 * Created on 20.06.2019 20:58.
 * @author Lars Artmann | LartyHD
 */
interface LoadedPlanetable : Planetable {
    override val planet: LoadedPlanet
}