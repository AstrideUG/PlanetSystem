package me.devsnox.planetsystem.api.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.UUID;
import java.util.function.Consumer;

@Data
@AllArgsConstructor
@SuppressWarnings("WeakerAccess")
public class PlanetLocation {

    private final UUID planetID;
    private final Vector vector;
    private final float yaw;
    private final float pitch;

    public PlanetLocation(final UUID planetID) {
        this(planetID, new Vector(), 0, 0);
    }

    public void toBukkitLocation(final Consumer<Location> request) {
        System.out.println("Start toBukkitLocation");
        System.out.println(this.getPlanetID());
        System.out.println(Holder.Impl.holder.getPlanetData().getLoadedPlanets());
        final LoadedPlanet loadedPlanet = Holder.Impl.holder.getPlanetData().getLoadedPlanet(this.getPlanetID());
        System.out.println(loadedPlanet);
        final Location location = loadedPlanet.getMiddle().add(vector);
        System.out.println(location);

        location.setYaw(getYaw());
        location.setPitch(getPitch());

        request.accept(location);
        System.out.println("End toBukkitLocation");
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
