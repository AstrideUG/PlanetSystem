package me.devsnox.planetsystem.api;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface Planet {

    int getId();

    Location getMiddle();
    Location getSpawnLocation();

    void setSpawnLocation(Location location);

    List<UUID> getMembers();

    int getLevel();

    int getTotalExp();

    int getExpToNextLevel();
}
