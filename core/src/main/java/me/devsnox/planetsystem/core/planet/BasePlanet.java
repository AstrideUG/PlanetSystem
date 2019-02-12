package me.devsnox.planetsystem.core.planet;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import lombok.Data;
import lombok.NonNull;
import me.devsnox.dynamicminecraftnetwork.api.DynamicNetworkFactory;
import me.devsnox.planetsystem.api.events.PlanetCreatedEvent;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static me.devsnox.planetsystem.api.holder.Holder.Impl.holder;

@Data
public final class BasePlanet implements Planet
{
	private final @NonNull UUID uniqueID;
	private final @NonNull String name;
	private final @NonNull UUID ownerUniqueID;
	private final @NonNull List<UUID> members;
	private @NonNull byte size;
	private @NonNull PlanetLocation spawnLocation;
	
	@Override
	public void load(final Consumer<LoadedPlanet> result)
	{
		GridHandler grid = holder.getGridHandler();
		Location location = grid.getEmptyLocation();
		BaseLoadedPlanet loadedPlanet = new BaseLoadedPlanet(this, location, grid.getMaxSize());
		holder.getPlanetData().getLoadedPlanets().add(loadedPlanet);
//		System.out.println("load Schem" + this.ownerUniqueID);
		DynamicNetworkFactory.dynamicNetworkAPI.getSchematic(this.uniqueID, schematic -> {
//			System.out.println("schem" + this.ownerUniqueID);
//			final Clipboard clipboard = schematic.getClipboardHolder().getClipboard();
//			System.out.println(clipboard.getRegion());
//			System.out.println(clipboard.getEntities());
//			System.out.println(clipboard.getOrigin());
//			System.out.println(clipboard.getDimensions());
//			try
//			{
//				schematic.paste(location);
//			} catch (WorldEditException e)
//			{
//				e.printStackTrace();
//			}
			schematic.paste(FaweAPI.getWorld(location.getWorld().getName()), new Vector(location.getX(), location.getY(), location.getZ()));
		});
//		PlanetLocation min = loadedPlanet.getInner().getMin();
//		PlanetLocation max = loadedPlanet.getInner().getMax();
//		org.bukkit.util.Vector maxV = max.getVector().clone().add(new org.bukkit.util.Vector(1, 0, 1));
//		org.bukkit.util.Vector midpoint = max.getVector().clone().midpoint(min.getVector());
		Bukkit.getPluginManager().callEvent(new PlanetCreatedEvent(loadedPlanet));
		result.accept(loadedPlanet);
//		System.out.println("finish load Schem" + this.ownerUniqueID);
	}
}
