package me.devsnox.planetsystem.core.database;

import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.planet.BasePlanet;
import org.mongodb.morphia.annotations.*;

import java.util.List;
import java.util.UUID;

@Entity(value = "planets", noClassnameStored = true)
public class DatabasePlanet implements me.devsnox.planetsystem.api.database.DatabasePlanet {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;

    @Indexed
    private String name;

    @Property("owner")
    private UUID ownerUniqueId;

    private List<UUID> members;

    private byte size;

    private PlanetLocation planetLocation;

    public DatabasePlanet() {

    }

    public DatabasePlanet(final UUID uniqueId, final String name, final UUID ownerUniqueId, final List<UUID> members, final byte size, final PlanetLocation planetLocation) {
        this.uuid = uniqueId;
        this.name = name;
        this.ownerUniqueId = ownerUniqueId;
        this.members = members;
        this.size = size;
        this.planetLocation = planetLocation;
    }

    public static DatabasePlanet by(final Planet planet) {
        return new DatabasePlanet(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public UUID getOwnerUniqueId() {
        return ownerUniqueId;
    }

    public void setOwnerUniqueId(final UUID ownerUniqueId) {
        this.ownerUniqueId = ownerUniqueId;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(final List<UUID> members) {
        this.members = members;
    }

    public byte getSize() {
        return size;
    }

    public void setSize(final byte size) {
        this.size = size;
    }

    public PlanetLocation getPlanetLocation() {
        return planetLocation;
    }

    public void setPlanetLocation(final PlanetLocation planetLocation) {
        this.planetLocation = planetLocation;
    }

    public BasePlanet toBasePlanet() {
        return new BasePlanet(this.uuid, this.name, this.ownerUniqueId, this.members, this.size, this.planetLocation);
    }

    @Override
    public Planet toPlanet() {
        return toBasePlanet();
    }

}
