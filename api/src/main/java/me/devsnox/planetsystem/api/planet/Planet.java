package me.devsnox.planetsystem.api.planet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface Planet {

    //TODO: Change to ObjectId (Hex-String)
    int getId();

    int getSize();

    void setSize(int size);

    Location getX();

    Location getZ();

    boolean isInside(Player player);

    boolean isInside(Location location);

    boolean isInside(Block block);

    Location getCenter();

    Location getSpawnLocation();
    void setSpawnLocation(Location location);

    List<UUID> getMembers();

    int getLevel();
    int getTotalExp();
    int getExpToNextLevel();
}
