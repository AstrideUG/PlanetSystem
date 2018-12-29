package me.devsnox.planetsystem.core.planet;

import lombok.NonNull;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class BasePlanet implements Planet {

    private final UUID uniqueID;
    private final String name;
    private final UUID ownerUniqueID;
    private final List<UUID> members;
    private byte size;
    private PlanetLocation spawnLocation;

    public BasePlanet(@NonNull final UUID uniqueID, @NonNull final String name, @NonNull final UUID ownerUniqueID, final List<UUID> members, final byte size, @NonNull final PlanetLocation spawnLocation) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.ownerUniqueID = ownerUniqueID;
        this.members = members;
        this.size = size;
        this.spawnLocation = spawnLocation;
    }

    @Override
    public UUID getUniqueID() {
        return uniqueID;
    }

    @Override
    public UUID getOwnerUniqueID() {
        return ownerUniqueID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<UUID> getMembers() {
        return members;
    }


    @Override
    public byte getSize() {
        return size;
    }

    @Override
    public void setSize(final byte size) {
        this.size = size;
    }

    @Override
    public PlanetLocation getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public void setSpawnLocation(final PlanetLocation planetLocation) {
        this.spawnLocation = planetLocation;
    }

    @Override
    public void load(final Consumer<LoadedPlanet> result) {
        Holder.Impl.holder.getPlanetData().load(this.uniqueID, result);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BasePlanet that = (BasePlanet) o;
        return size == that.size &&
                Objects.equals(uniqueID, that.uniqueID) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ownerUniqueID, that.ownerUniqueID) &&
                Objects.equals(members, that.members) &&
                Objects.equals(spawnLocation, that.spawnLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueID, name, ownerUniqueID, members, size, spawnLocation);
    }

    @Override
    public String toString() {
        return "BasePlanet{" +
                "uniqueID=" + uniqueID +
                ", name='" + name + '\'' +
                ", ownerUniqueID=" + ownerUniqueID +
                ", members=" + members +
                ", size=" + size +
                ", spawnLocation=" + spawnLocation +
                '}';
    }

}
