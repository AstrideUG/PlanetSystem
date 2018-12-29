package me.devsnox.planetsystem.api.holder.data;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public interface PlayerData {

    void load(UUID uuid, Consumer<PlanetPlayer> request);

    void save(UUID uuid);

    @Nullable
    default PlanetPlayer getPlayer(final UUID uuid) {
        System.out.println(getPlayers());
        for (final PlanetPlayer player : getPlayers()) if (player.getUUID().equals(uuid)) return player;
        return null;
    }

    Set<PlanetPlayer> getPlayers();

    //Planet getPlanet(UUID owner);

    //LoadedPlanet getLoadedPlanet(UUID owner);

}
