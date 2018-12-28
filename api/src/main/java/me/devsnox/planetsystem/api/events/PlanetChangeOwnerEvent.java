package me.devsnox.planetsystem.api.events;

import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlanetChangeOwnerEvent extends Event implements Cancellable {

    private Planet planet;

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
