package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.events.PlanetCreatedEvent;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.core.utils.ThreadUtils;
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerListener implements Listener {


    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();

        System.out.println("onJoin");
        Holder.Impl.holder.getDatabaseHandler().create(UUID.randomUUID(), uniqueId, aBoolean -> {
//            if (aBoolean) {
            System.out.println("create " + uniqueId + aBoolean);
            Holder.Impl.holder.getPlanetData().load(uniqueId, loadedPlanet -> {
                if (aBoolean) Bukkit.getPluginManager().callEvent(new PlanetCreatedEvent(loadedPlanet));
                System.out.println(aBoolean);
                System.out.println(loadedPlanet);
                System.out.println(player);
                Holder.Impl.holder.getPlayerData().load(uniqueId, planetPlayer -> {
                    System.out.println(planetPlayer);
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
    public void on(final PlayerDisconnectEvent event) {
        Holder.Impl.holder.getPlanetData().save(event.getPlayer().getUniqueId());
    }

}
