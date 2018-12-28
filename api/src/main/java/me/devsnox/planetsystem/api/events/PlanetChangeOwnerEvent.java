package me.devsnox.planetsystem.api.events;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlanetChangeOwnerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final UUID oldOwner;
    private final UUID newOwner;

    private final LoadedPlanet planet;

    public PlanetChangeOwnerEvent(final UUID newOwner, final LoadedPlanet planet) {
        this.oldOwner = planet.getOwnerUniqueID();
        this.newOwner = newOwner;
        this.planet = planet;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public LoadedPlanet getPlanet() {
        return this.planet;
    }

    public UUID getOldOwner() {
        return this.oldOwner;
    }

    public UUID getNewOwner() {
        return this.newOwner;
    }
}
