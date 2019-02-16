package de.astride.planetsystem.core.database;

import de.astride.planetsystem.api.database.DatabasePlayer;
import lombok.Getter;
import de.astride.planetsystem.api.planet.Planet;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;
import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
@Getter
public class BasicDatabasePlayer implements DatabasePlayer
{
	@Id
	@Indexed(options = @IndexOptions(unique = true))
	private UUID uuid;
	private UUID planetUniqueId;
	private List<UUID> memberedPlanets;
	
	public BasicDatabasePlayer()
	{
	}
	
	public BasicDatabasePlayer(final UUID uuid, final UUID planetUniqueId, final List<UUID> memberedPlanets)
	{
		this.uuid = uuid;
		this.planetUniqueId = planetUniqueId;
		this.memberedPlanets = memberedPlanets;
	}
	
	public static BasicDatabasePlayer by(final Planet planet)
	{
		return new BasicDatabasePlayer(planet.getOwnerUniqueID(), planet.getUniqueID(), planet.getMembers());
	}
}
