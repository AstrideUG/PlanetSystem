package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.core.api.InternalFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        InternalFactory.internalAPI.loadPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        InternalFactory.internalAPI.savePlayer(event.getPlayer().getUniqueId());
    }
}
