package de.astride.planetsystem.core.commands.modules.expand

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.astride.planetsystem.api.location.PlanetLocation
import de.astride.planetsystem.api.player.PlanetPlayer
import de.astride.planetsystem.core.location.BaseRegion
import de.astride.planetsystem.core.log.MessageKeys
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.event.inventory.InventoryType
import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.03.2019 04:26.
 * Current Version: 1.0 (07.03.2019 - 07.03.2019)
 */
class ExpandCommandTest {


    companion object {
        init {

            val server = mock<Server>()
            whenever(server.logger).thenReturn(mock())
            whenever(server.createInventory(mock(), mock<InventoryType>(), "")).thenReturn(mock())

            Bukkit.setServer(server)

        }
    }

//    @Test
//    fun `"player is not allowed to execute any expand commands when not on his planet (allow)"`() {
//
//        /* Given */
//        val command = mock<ExpandCommand>()
//
//        val planetPlayer: PlanetPlayer = mock()
//        whenever(planetPlayer.planet).thenReturn(mock())
//        whenever(planetPlayer.player).thenReturn(mock())
//        whenever(planetPlayer.player.location).thenReturn(mock())
////        whenever(planetPlayer.isOnHisPlanet()).thenReturn(true)
//
//        whenever(command.execute(planetPlayer, emptyArray())).thenCallRealMethod()
//
//        /* When */
//        command.execute(planetPlayer, emptyArray())
//
//        /* Then */
////        verify(planetPlayer.isOnHisPlanet())
//        verify(planetPlayer.player)
//        verify(planetPlayer.player.openInventory(mock<Inventory>()))
//
//    }

    @Test
    fun `"player is not allowed to execute any expand commands when not on his planet (disallow)"`() {

        /* Given */
        val command = mock<ExpandCommand>()

        val planetPlayer: PlanetPlayer = mock()
        whenever(planetPlayer.logger).thenReturn(mock())
        whenever(planetPlayer.planet).thenReturn(mock())
        val planetLocation = PlanetLocation(null)
        whenever(planetPlayer.planet.inner).thenReturn(BaseRegion(planetLocation, planetLocation))
        whenever(planetPlayer.player).thenReturn(mock())
        val location = Location(mock(), 10.0, 10.0, 10.0)
        whenever(planetPlayer.planet.middle).thenReturn(location)
        whenever(planetPlayer.player.location).thenReturn(location)
        whenever(command.execute(planetPlayer, emptyArray())).thenCallRealMethod()

        /* When */
        command.execute(planetPlayer, emptyArray())

        /* Then */
        verify(planetPlayer.logger).warn(MessageKeys.COMMANDS_EXPAND_FAILED_NOT_ON_OWN_PLANET)

    }

}