package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.PlanetAPI;
import me.devsnox.planetsystem.api.PlanetFactory;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlanetListener implements Listener {

    private PlanetAPI planetAPI;

    public PlanetListener() {
        this.planetAPI = PlanetFactory.planetAPI;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        PlanetPlayer player = this.planetAPI.getPlayer(event.getPlayer());
        if(!player.canBuild(event.getBlock())) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        PlanetPlayer player = this.planetAPI.getPlayer(event.getPlayer());
        if(!player.canBuild(event.getBlock())) event.setCancelled(true);
    }
}
