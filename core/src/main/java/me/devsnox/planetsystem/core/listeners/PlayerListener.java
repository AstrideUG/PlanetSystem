package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.core.utils.ThreadUtils;
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
        //TODO: Better
        Holder.Impl.holder.getDatabaseHandler().create(UUID.randomUUID(), player.getUniqueId());

        Holder.Impl.holder.getPlanetData().load(player.getUniqueId(), loadedPlanet -> {
            if (!player.hasPlayedBefore())
                loadedPlanet.getSpawnLocation().toBukkitLocation(location -> ThreadUtils.sync(() -> player.teleport(location)));
        });
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Holder.Impl.holder.getPlanetData().save(event.getPlayer().getUniqueId());
    }

}
