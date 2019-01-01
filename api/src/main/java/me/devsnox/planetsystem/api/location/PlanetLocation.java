package me.devsnox.planetsystem.api.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("WeakerAccess")
public class PlanetLocation {

    private UUID planetID;
    private Vector vector;
    private float yaw;
    private float pitch;

    public PlanetLocation(final UUID planetID) {
        this(planetID, new Vector(), 0, 0);
    }


    public Location toBukkitLocation(final LoadedPlanet loadedPlanet) {
        System.out.println("Start toBukkitLocation(LoadedPlanet)");
        System.out.println(vector);
        System.out.println(loadedPlanet);

        final Location location = vector.clone().add(loadedPlanet.getMiddle().toVector()).toLocation(Holder.Impl.holder.getWorld());

        location.setYaw(getYaw());
        location.setPitch(getPitch());

        System.out.println("End toBukkitLocation(LoadedPlanet)");
        return location;
    }


    public Location toBukkitLocation() {
        return toBukkitLocation(Holder.Impl.holder.getPlanetData().getLoadedPlanet(this.getPlanetID()));
    }

    public com.sk89q.worldedit.Vector toWEVector() {
        return new com.sk89q.worldedit.Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static PlanetLocation create(final Vector vector, final float yaw, final float pitch, final UUID planetID) {
        return new PlanetLocation(planetID, vector, yaw, pitch);
    }

    public static PlanetLocation create(final Location location, final UUID planetID) {
        return new PlanetLocation(planetID, location.toVector(), location.getYaw(), location.getPitch());
    }

    public static PlanetLocation create(final Vector input, final Vector middle, final float yaw, final float pitch, final UUID planetID) {
        return new PlanetLocation(planetID, input.clone().subtract(middle) /* Location - middle-point */, yaw, pitch);
    }

    public static PlanetLocation create(final Location location, final Vector middle, final UUID planetID) {
        return create(location.toVector(), middle, location.getYaw(), location.getPitch(), planetID);
    }

    public static PlanetLocation create(final Location location, final Location middle, final UUID planetID) {
        return create(location, middle.toVector(), planetID);
    }

    public static PlanetLocation create(final Location location, final LoadedPlanet planet) {
        return create(location, planet.getMiddle(), planet.getUniqueID());
    }

}
