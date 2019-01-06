package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlanetListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(final BlockPlaceEvent event) {
        this.blockBuild(event, event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        this.blockBuild(event, event.getBlock(), event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)  {
        if(Holder.Impl.holder.getWorld().equals(event.getPlayer().getWorld())) {
            if (event.getAction() == Action.PHYSICAL) {
                Block block = event.getClickedBlock();
                if (block == null) return;

                if (block.getType() == Material.SOIL) {
                    event.setUseInteractedBlock(Event.Result.DENY);
                    event.setCancelled(true);
                }
            }
        }
    }

    private void blockBuild(final Cancellable cancellable, final Block block, final Player player) {
        if (!Holder.Impl.holder.getWorld().equals(player.getWorld())) return;
        final PlanetPlayer planetPlayer = Holder.Impl.holder.getPlayerData().getPlayer(player.getUniqueId());
        if (planetPlayer != null && planetPlayer.canNotBuild(block)) cancellable.setCancelled(true);

        if (cancellable.isCancelled()) {
            planetPlayer.getLogger().warn("block_listener.deny_build");
        }
    }

}
