package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.events.PlanetCreatedEvent;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.core.utils.ThreadUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {


    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();

        Holder.Impl.holder.getDatabaseHandler().create(UUID.randomUUID(), uniqueId, aBoolean ->
                Holder.Impl.holder.getPlanetData().load(uniqueId, loadedPlanet -> {
                    Bukkit.getPluginManager().callEvent(new PlanetCreatedEvent(loadedPlanet));
                    Holder.Impl.holder.getPlayerData().load(uniqueId, planetPlayer ->
                            loadedPlanet.getSpawnLocation().toBukkitLocation(location -> ThreadUtils.sync(() -> player.teleport(location))));
                }));
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Holder.Impl.holder.getPlanetData().save(event.getPlayer().getUniqueId());
    }

}
