package me.devsnox.planetsystem.core.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.core.planet.BasePlanet;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(value = "planets", noClassnameStored = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabasePlanet implements me.devsnox.planetsystem.api.database.DatabasePlanet {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;
    @Indexed
    private String name;
    @Indexed
    @Property("owner")
    private UUID ownerUniqueId;
    private List<UUID> members;
    private byte size;
    private PlanetLocation planetLocation;

    public static DatabasePlanet by(final Planet planet) {
        return new DatabasePlanet(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
    }

    public List<UUID> getMembers() {
        if (members == null) return new ArrayList<>();
        else return members;
    }

    @Override
    public BasePlanet clone() {
        return new BasePlanet(this.uuid, this.name, this.ownerUniqueId, this.getMembers(), this.size, this.planetLocation);
    }

    @Override
    public Planet toPlanet() {
        return clone();
    }

}
