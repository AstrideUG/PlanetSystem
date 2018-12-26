package me.devsnox.planetsystem.core.listeners;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.api.InternalAPI;
import me.devsnox.planetsystem.core.api.InternalFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlanetListener implements Listener {

    private InternalAPI internalAPI;

    public PlanetListener() {
        this.internalAPI = InternalFactory.internalAPI;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Planet planet = this.internalAPI.getPlanetAPI().getPlanet(player.getUniqueId());

        if (!planet.isInside(event.getBlock())) {
            event.setBuild(false);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Planet planet = this.internalAPI.getPlanetAPI().getPlanet(player.getUniqueId());

        if (!planet.isInside(event.getBlock())) {
            event.setCancelled(true);
        }
    }
}
