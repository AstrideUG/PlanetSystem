package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlanetListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(final BlockPlaceEvent event) {
        blockBuild(event, event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        blockBuild(event, event.getBlock(), event.getPlayer());
    }

    private void blockBuild(final Cancellable cancellable, final Block block, final Player player) {
        if (!Holder.Impl.holder.getWorld().equals(player.getWorld())) return;
        final PlanetPlayer planetPlayer = Holder.Impl.holder.getPlayerData().getPlayer(player.getUniqueId());
        if (planetPlayer != null && !planetPlayer.canNotBuild(block)) cancellable.setCancelled(true);
    }

}
