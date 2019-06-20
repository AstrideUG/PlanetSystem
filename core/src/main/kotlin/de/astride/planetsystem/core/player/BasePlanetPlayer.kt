/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.players
import de.astride.planetsystem.api.log.KeyLogger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.functions.toDatabasePlayer
import de.astride.planetsystem.core.log.BasePlayerKeyLogger
import org.bukkit.entity.Player

class BasePlanetPlayer(
    override val player: Player,
    override val planet: LoadedPlanet
) : PlanetPlayer {

    override val logger: KeyLogger = BasePlayerKeyLogger(player)

    override fun unload() {
        save()
        players -= this
    }

    override fun save() {
        databaseHandler.savePlayer(planet.toDatabasePlayer())
//        DynamicNetworkFactory.dynamicNetworkAPI.saveSchematic(innerPlanet.uniqueID.uniqueID, innerPlanet.schematic)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BasePlanetPlayer) return false

        if (player != other.player) return false
        if (planet != other.planet) return false
        if (logger != other.logger) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + planet.hashCode()
        result = 31 * result + logger.hashCode()
        return result
    }

    override fun toString(): String = "BasePlanetPlayer(player=$player, innerPlanet=$planet)"

}
