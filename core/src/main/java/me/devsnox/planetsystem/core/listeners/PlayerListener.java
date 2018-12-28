package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.PlanetFactory;
import me.devsnox.planetsystem.core.api.InternalHandler;
import me.devsnox.planetsystem.core.utils.ThreadUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private InternalHandler internalHandler;

    public PlayerListener(InternalHandler internalHandler) {

    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.internalHandler.autoLoadPlanetByPlayerId(event.getPlayer().getUniqueId());

        PlanetFactory.planetAPI.getPlanet(player.getUniqueId()).load(loadedPlanet -> loadedPlanet.getSpawnLocation().toBukkitLocation(location -> ThreadUtils.sync(() -> player.teleport(location)))); //TODO: Result
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.internalHandler.autoSavePlanetByPlayerId(event.getPlayer().getUniqueId());
    }
}
