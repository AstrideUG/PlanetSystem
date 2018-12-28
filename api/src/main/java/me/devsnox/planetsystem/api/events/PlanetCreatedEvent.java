package me.devsnox.planetsystem.api.events;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlanetCreatedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final LoadedPlanet planet;

    public PlanetCreatedEvent(final LoadedPlanet planet) {
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
}
