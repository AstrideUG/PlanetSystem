package me.devsnox.planetsystem.api.location;

import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;

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

    static void createFromBukkitLocation(Planet planet, Location location) {
        Factory.createFromBukkitLocation(planet, location);
    }
}

final class Factory {

    static PlanetLocation createFromBukkitLocation(Planet planet, Location location) {
        Location finalLocation = planet.getMiddle().add(location);
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

    public PlanetLocationImpl(UUID planetID, double x, double y, double z, float yaw, float pitch) {
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

    public void setPlanetID(UUID planetID) {
        this.planetID = planetID;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetLocationImpl that = (PlanetLocationImpl) o;
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
