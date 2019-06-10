package de.astride.planetsystem.core.listeners

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.03.2019 02:49.
 * Current Version: 1.0 (07.03.2019 - 07.03.2019)
 */
class PlayerListenerTest {

//    @Test
//    fun `if pvp value is true`() {
//
//        /* Given */
//        val playerListener = mock<PlayerListener>()//(useConstructor = UseConstructor.withArguments(javaPlugin, holder))
//
//        val event = mock<EntityDamageByEntityEvent>()
//        val target = mock<Entity>()
//        val damager = mock<Entity>()
//        whenever(event.entity).thenReturn(target)
//        whenever(event.entity.type).thenReturn(EntityType.PLAYER)
//        whenever(event.damager).thenReturn(damager)
//        whenever(playerListener.onEntityDamageByEntityEvent(event)).thenCallRealMethod()
//
//        /* When */
//        playerListener.onEntityDamageByEntityEvent(event)
//
//        /* Then */
//        verify(playerListener).onEntityDamageByEntityEvent(event)
//        assert(event.isCancelled)
//
////        assert(Flags.PvP.value) { "Flags.PvP.value is not true" }
//
//    }


//    @Test
//    fun `called if pvp is on`() {
//
//        /* Given */
//        Flags.PvP.value = true
//
//        val playerListener = mock<PlayerListener>()
//        val event = mock<EntityDamageByEntityEvent>()
//
//        whenever(playerListener.onEntityDamageByEntityEvent(event)).thenCallRealMethod()
//
//        /* When */
//        playerListener.onEntityDamageByEntityEvent(event)
//
//        /* Then */
//        verify(playerListener).onEntityDamageByEntityEvent(event)
//        verifyZeroInteractions(event)
//
//    }
//
//    @Test
//    fun `called if pvp is off`() {
//
//        /* Given */
//        Flags.PvP.value = false
//
//        val playerListener = mock<PlayerListener>()
//        val event = mock<EntityDamageByEntityEvent>()
//        val target = mock<Entity>()
//
//        whenever(event.damager).thenReturn(mock())
//        whenever(event.entity).thenReturn(target)
//        whenever(event.entityType).thenReturn(EntityType.FISHING_HOOK) //EntityType can be anything but PLAYER
//
//        whenever(playerListener.onEntityDamageByEntityEvent(event)).thenCallRealMethod()
//
//        /* When */
//        playerListener.onEntityDamageByEntityEvent(event)
//
//        /* Then */
//        verify(playerListener).onEntityDamageByEntityEvent(event)
//        verify(event).entityType
//        verify(event).damager
//
//    }


}
