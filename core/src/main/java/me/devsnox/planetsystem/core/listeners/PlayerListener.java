package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.events.PlanetCreatedEvent;
import me.devsnox.planetsystem.core.utils.ThreadUtils;
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static me.devsnox.planetsystem.api.holder.Holder.Impl.holder;

public class PlayerListener implements Listener {


    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();

        holder.getDatabaseHandler().create(UUID.randomUUID(), uniqueId, aBoolean -> {
//            if (aBoolean) {
            holder.getPlanetData().load(uniqueId, loadedPlanet -> {
                if (aBoolean) Bukkit.getPluginManager().callEvent(new PlanetCreatedEvent(loadedPlanet));
                holder.getPlayerData().load(uniqueId, planetPlayer -> {
                    ThreadUtils.sync(() -> player.teleport(loadedPlanet.getSpawnLocation().toBukkitLocation()));
                });

            });
           /* } else {
                final Logger logger = new BasePlayerKeyLogger(player, new HashMap<>()); //TODO: Make it better
                logger.warn("Events.Player.Join.Failed.CanNotCreatedAPlanet");
            }*/
            System.out.println("stop onJoin");
        });
    }

    @EventHandler
    public void onQuit(final PlayerDisconnectEvent event) {
        holder.getPlanetData().unload(event.getPlayer().getUniqueId());
        holder.getPlayerData().unload(event.getPlayer().getUniqueId());
    }

}
