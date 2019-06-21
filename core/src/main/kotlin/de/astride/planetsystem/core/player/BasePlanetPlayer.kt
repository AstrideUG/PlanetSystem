/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.player

import de.astride.planetsystem.api.holder.databaseHandler
import de.astride.planetsystem.api.holder.players
import de.astride.planetsystem.api.log.KeyLogger
import de.astride.planetsystem.api.planet.LoadedPlanet
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.api.proxies.UniqueID
import de.astride.planetsystem.core.functions.toBasicOfflinePlayer
import de.astride.planetsystem.core.log.BasePlayerKeyLogger
import org.bukkit.entity.Player

class BasePlanetPlayer(
    override val player: Player,
    override val planet: LoadedPlanet,
    override val history: Set<UniqueID>,
    override val logger: KeyLogger = BasePlayerKeyLogger(player)
) : PlanetPlayer {

    override fun unload() {
        save()
        players -= this
    }

    override fun save(): Unit = databaseHandler.savePlayer(toBasicOfflinePlayer())

}
