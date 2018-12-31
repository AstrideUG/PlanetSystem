package me.devsnox.planetsystem.api.location;


import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public interface Region {

    PlanetLocation getMin();

    PlanetLocation getMax();


    default boolean isInside(final Vector vector) {
        return vector.isInAABB(getMin().getVector(), getMax().getVector());
    }

    default boolean isInside(final PlanetLocation location) {
/*        getMin().getVector().subtract(location.getVector());
        getMax().getVector().subtract(location.getVector());

//        2045, 126, 10;
//        2170, 150, 70;
//
//        2080, 140, 50;

        Vector min = location.getVector().clone().subtract(getMin().getVector());//35, 14, 40
        Vector max = location.getVector().clone().subtract(getMax().getVector());//-90, -10, -20

        //35-90, 14-10, 40-20   = -55, 4, 20
        //35+90, 14+10, 40+20   = 125, 24, 60

        //-90-10-20 = -120
        //90+10+20  = 120

        //35+14+40 = 89*/

        return location.getVector().isInAABB(getMin().getVector(), getMax().getVector());
    }

    default boolean isInside(final Location location) {
        return isInside(location.toVector());
    }

    default boolean isInside(final Player player) {
        return isInside(player.getLocation());
    }

    default boolean isInside(final Block block) {
        return isInside(block.getLocation());
    }
}
