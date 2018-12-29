package me.devsnox.planetsystem.api.holder.data;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public interface PlayerData {

    void load(UUID uuid, Consumer<PlanetPlayer> request);

    void save(UUID uuid);

    @Nullable
    default PlanetPlayer getPlayer(UUID uuid) {
        for (PlanetPlayer player : getPlayers()) if (player.getUUID() == uuid) return player;
        return null;
    }

    Set<PlanetPlayer> getPlayers();

    //Planet getPlanet(UUID owner);

    //LoadedPlanet getLoadedPlanet(UUID owner);

}
