package me.devsnox.planetsystem.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(final AsyncPlayerPreLoginEvent event) {

    }


    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
    }
}
