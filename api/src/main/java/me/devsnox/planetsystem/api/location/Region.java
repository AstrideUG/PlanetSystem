package me.devsnox.planetsystem.api.location;


import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface Region {

    Location getMin();

    Location getMax();


    default boolean isInside(final Location location) {
        //TODO: Better handling (using add and substract (comparing))
        return getMin().getX() <= location.getX() && getMax().getX() >= location.getX() &&
                getMin().getY() <= location.getY() && getMax().getY() >= location.getY() &&
                getMin().getZ() <= location.getZ() && getMax().getZ() >= location.getZ();
    }

    default boolean isInside(final Player player) {
        return isInside(player.getLocation());
    }

    default boolean isInside(final Block block) {
        return isInside(block.getLocation());
    }
}
