package me.devsnox.planetsystem.api.planet;

import com.boydti.fawe.object.schematic.Schematic;
import me.devsnox.planetsystem.api.location.Region;
import org.bukkit.Location;

public interface LoadedPlanet extends Planet {

    Region getInner();

    Region getOuter();

    Location getMiddle();

    Schematic getSchematic();

}
