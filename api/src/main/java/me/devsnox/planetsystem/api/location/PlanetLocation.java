package me.devsnox.planetsystem.api.location;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class PlanetLocation {

    private UUID planetID;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public PlanetLocation() {

    }

    public PlanetLocation(final UUID planetID, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.planetID = planetID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public UUID getPlanetID() {
        return planetID;
    }

    public void setPlanetID(final UUID planetID) {
        this.planetID = planetID;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PlanetLocation that = (PlanetLocation) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.z, z) == 0 &&
                Float.compare(that.yaw, yaw) == 0 &&
                Float.compare(that.pitch, pitch) == 0 &&
                planetID.equals(that.planetID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planetID, x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        return "PlanetLocationImpl{" +
                "planetID=" + planetID +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }

    public void toBukkitLocation(final Consumer<Location> request) {
        System.out.println(this.getPlanetID());
        System.out.println(Holder.Impl.holder.getPlanetData().getLoadedPlanets());
        final LoadedPlanet loadedPlanet = Holder.Impl.holder.getPlanetData().getLoadedPlanet(this.getPlanetID());
        System.out.println(loadedPlanet);
        final Location location = loadedPlanet.getMiddle().subtract(getX(), getY(), getZ());

        location.setYaw(getYaw());
        location.setPitch(getPitch());

        request.accept(location);
    }

    public static PlanetLocation createFromBukkitLocation(final LoadedPlanet planet, final Location location) {
        final Location finalLocation = planet.getMiddle().add(location);
        return new PlanetLocation(planet.getUniqueID(), finalLocation.getX(), finalLocation.getY(), finalLocation.getZ(), finalLocation.getYaw(), finalLocation.getPitch());
    }

    public static PlanetLocation createPlanetLocation(final UUID planetId, final double x, final double y, final double z, final float yaw, final float pitch) {
        return new PlanetLocation(planetId, x, y, z, yaw, pitch);
    }
}
