package me.devsnox.planetsystem.core.planet;

import lombok.Data;
import lombok.NonNull;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Data
public class BasePlanet implements Planet {

    private final @NonNull UUID uniqueID;
    private final @NonNull String name;
    private final @NonNull UUID ownerUniqueID;
    private final @NonNull List<UUID> members;
    private @NonNull byte size;
    private @NonNull PlanetLocation spawnLocation;

    @Override
    public void load(final Consumer<LoadedPlanet> result) {
        Holder.Impl.holder.getPlanetData().load(this.uniqueID, result);
    }

}
