package me.devsnox.planetsystem.api.location;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public interface PlanetLocation {

    UUID getPlanetID();

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);

    double getZ();

    void setZ(double z);

    float getYaw();

    void setYaw(float yaw);

    float getPitch();

    void setPitch(float pitch);

    default void toBukkitLocation(final Consumer<Location> request) {
        Holder.Impl.holder.getPlanetData().load(this.getPlanetID(), loadedPlanet -> {
            final Location location = loadedPlanet.getMiddle().subtract(getX(), getY(), getZ());

            location.setYaw(getYaw());
            location.setPitch(getPitch());

            request.accept(location);
        });
    }

    static PlanetLocation createFromBukkitLocation(final LoadedPlanet planet, final Location location) {
        return Factory.createFromBukkitLocation(planet, location);
    }
}

final class Factory {

    static PlanetLocation createFromBukkitLocation(final LoadedPlanet planet, final Location location) {
        final Location finalLocation = planet.getMiddle().add(location);
        return new PlanetLocationImpl(planet.getUniqueID(), finalLocation.getX(), finalLocation.getY(), finalLocation.getZ(), finalLocation.getYaw(), finalLocation.getPitch());
    }
}

class PlanetLocationImpl implements PlanetLocation {

    private UUID planetID;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public PlanetLocationImpl(final UUID planetID, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.planetID = planetID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public UUID getPlanetID() {
        return planetID;
    }

    public void setPlanetID(final UUID planetID) {
        this.planetID = planetID;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(final double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void setZ(final double z) {
        this.z = z;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PlanetLocationImpl that = (PlanetLocationImpl) o;
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
}
