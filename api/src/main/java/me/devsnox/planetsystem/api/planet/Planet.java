package me.devsnox.planetsystem.api.planet;

import me.devsnox.planetsystem.api.location.PlanetLocation;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface Planet {

    UUID getUniqueID();

    UUID getOwnerUniqueID();


    String getName();

    List<UUID> getMembers();

    byte getSize();

    void setSize(byte size);

    PlanetLocation getSpawnLocation();

    void setSpawnLocation(PlanetLocation planetLocation);

    void load(Consumer<LoadedPlanet> result);

}
