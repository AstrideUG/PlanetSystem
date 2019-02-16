package de.astride.planetsystem.core.database.entities;

import de.astride.planetsystem.api.database.DatabasePlanet;
import de.astride.planetsystem.core.database.DatabaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import de.astride.planetsystem.api.location.PlanetLocation;
import de.astride.planetsystem.api.planet.Planet;
import de.astride.planetsystem.core.planet.BasePlanet;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity(value = "planets", noClassnameStored = true)
@Data
@NoArgsConstructor
public class BasicDatabasePlanet extends DatabaseEntity implements DatabasePlanet {
	@Indexed
	private String name;
	@Indexed
	@Property("owner")
	private UUID ownerUniqueId;
	private List<UUID> members;
	private byte size;
	private PlanetLocation planetLocation;
	private Map<String, Object> metaData;
	
	public BasicDatabasePlanet(UUID uuid, String name, UUID ownerUniqueId, List<UUID> members, byte size, PlanetLocation planetLocation)
	{
		super(uuid);
		this.name = name;
		this.ownerUniqueId = ownerUniqueId;
		this.members = members;
		this.size = size;
		this.planetLocation = planetLocation;
	}
	
	public static BasicDatabasePlanet by(final Planet planet)
	{
		return new BasicDatabasePlanet(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
	}
	
	public List<UUID> getMembers()
	{
		return this.members == null ? new ArrayList<>() : this.members;
	}
	
	@Override
	public Planet toPlanet()
	{
		return new BasePlanet(super.getUuid(), this.name, this.ownerUniqueId, this.getMembers(), this.size, this.planetLocation, this.metaData);
	}
}
