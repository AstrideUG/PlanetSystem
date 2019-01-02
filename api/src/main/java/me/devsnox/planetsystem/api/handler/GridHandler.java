package me.devsnox.planetsystem.api.handler;

import org.bukkit.Location;

public interface GridHandler {

    Location getEmptyLocation();

    int getId(Location location);

    void removeEntry(int i);

    int getMaxSize();

}
