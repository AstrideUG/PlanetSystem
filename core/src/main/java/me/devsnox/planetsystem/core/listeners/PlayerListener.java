package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.holder.Holder;
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

import static me.devsnox.planetsystem.api.holder.Holder.Impl.holder;

public class PlayerListener implements Listener {


    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();

        holder.getDatabaseHandler().create(UUID.randomUUID(), uniqueId, aBoolean ->
                holder.getPlayerData().load(uniqueId, planetPlayer -> {
                }));
    }

    @EventHandler
    public void onQuit(final PlayerDisconnectEvent event) {
        holder.getPlanetData().unload(event.getPlayer().getUniqueId());
        holder.getPlayerData().unload(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(holder.getPlanetData().getLoadedPlanetByOwner(event.getPlayer().getUniqueId()).getSpawnLocation().toBukkitLocation());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity().getType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER && Holder.Impl.holder.getWorld().equals(event.getEntity().getWorld())) event.setCancelled(true);
    }
}
