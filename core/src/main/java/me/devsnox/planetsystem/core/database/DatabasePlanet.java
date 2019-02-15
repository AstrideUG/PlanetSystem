package me.devsnox.planetsystem.core.database;

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
@NoArgsConstructor
public class DatabasePlanet implements me.devsnox.planetsystem.api.database.DatabasePlanet
{
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
	
	public DatabasePlanet(UUID uuid, String name, UUID ownerUniqueId, List<UUID> members, byte size, PlanetLocation planetLocation)
	{
		this.uuid = uuid;
		this.name = name;
		this.ownerUniqueId = ownerUniqueId;
		this.members = members;
		this.size = size;
		this.planetLocation = planetLocation;
	}
	
	public static DatabasePlanet by(final Planet planet)
	{
		return new DatabasePlanet(planet.getUniqueID(), planet.getName(), planet.getOwnerUniqueID(), planet.getMembers(), planet.getSize(), planet.getSpawnLocation());
	}
	
	public List<UUID> getMembers()
	{
		return this.members == null ? new ArrayList<>() : this.members;
	}
	
	@Override
	public Planet toPlanet()
	{
		return new BasePlanet(this.uuid, this.name, this.ownerUniqueId, this.getMembers(), this.size, this.planetLocation);
	}
}
