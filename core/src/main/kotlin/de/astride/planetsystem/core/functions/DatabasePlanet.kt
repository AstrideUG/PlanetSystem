package de.astride.planetsystem.core.functions

import de.astride.planetsystem.api.database.DatabasePlanet
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID
import de.astride.planetsystem.api.planet.Planet
import de.astride.planetsystem.core.planet.BasePlanet

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 26.02.2019 00:43.
 * Current Version: 1.0 (26.02.2019 - 26.02.2019)
 */
fun DatabasePlanet.toPlanet(): Planet = BasePlanet(
    UniqueID(uuid),
    name,
    Owner(ownerUniqueId),
    members.map(::Owner).toMutableList(),
    size,
    planetLocation
)