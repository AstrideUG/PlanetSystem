package me.devsnox.planetsystem.api.events;

import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlanetCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Planet planet;

    public PlanetCreateEvent(final Planet planet) {
        this.planet = planet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Planet getPlanet() {
        return planet;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
