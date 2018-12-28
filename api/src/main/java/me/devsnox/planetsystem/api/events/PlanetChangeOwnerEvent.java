package me.devsnox.planetsystem.api.events;

import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlanetChangeOwnerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final UUID oldOwner;
    private final UUID newOwner;

    private final Planet planet;

    public PlanetChangeOwnerEvent(final UUID newOwner, final Planet planet) {
        this.oldOwner = planet.getOwnerUniqueID();
        this.newOwner = newOwner;
        this.planet = planet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Planet getPlanet() {
        return planet;
    }

    public UUID getOldOwner() {
        return this.oldOwner;
    }

    public UUID getNewOwner() {
        return newOwner;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
