package me.devsnox.planetsystem.api.planet;

import com.boydti.fawe.object.schematic.Schematic;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface Planet extends PlanetInfo {

    int getSize();
    void setSize(int size);

    Location getMin();
    Location getMax();

    boolean isInside(Location location);
    default boolean isInside(Player player) {
        return isInside(player.getLocation());
    }
    default boolean isInside(Block block) {
        return isInside(block.getLocation());
    }

    Location getMiddle();

    Location getSpawnLocation();
    void setSpawnLocation(Location location);

    Schematic getSchematic();
}
