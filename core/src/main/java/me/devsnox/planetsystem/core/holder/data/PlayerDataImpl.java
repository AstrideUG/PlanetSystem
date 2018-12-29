package me.devsnox.planetsystem.core.holder.data;

import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlayerData;
import me.devsnox.planetsystem.core.database.DatabasePlayer;
import me.devsnox.planetsystem.core.player.BasePlanetPlayer;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class PlayerDataImpl implements PlayerData {

    private final Holder holder;
    private final Set<PlanetPlayer> players;

    public PlayerDataImpl(Holder holder) {
        this.holder = holder;
        this.players = new HashSet<>();
    }

    @Override
    public void load(UUID uuid, Consumer<PlanetPlayer> request) {
        final me.devsnox.planetsystem.api.database.DatabasePlayer databasePlayer = holder.getDatabaseHandler().getPlayer(uuid);

        holder.getPlanetData().load(databasePlayer.getPlanetUniqueId(), loadedPlanet -> {
            final List<Planet> members = new ArrayList<>();//TODO databasePlayer.getMemberedPlanets().stream().map(this::getPlanet).collect(Collectors.toList());
            final PlanetPlayer planetPlayer = new BasePlanetPlayer(Bukkit.getPlayer(uuid), loadedPlanet, members);

            holder.getPlayerData().getPlayers().add(planetPlayer);
            request.accept(planetPlayer);
        });
    }

    @Override
    public void save(final UUID uuid) {
        final PlanetPlayer player = holder.getPlayerData().getPlayer(uuid);
        if (player == null) return;
        final DatabasePlayer databasePlayer = DatabasePlayer.by(player.getPlanet());
        holder.getDatabaseHandler().savePlayer(databasePlayer);
    }

    @Override
    public Set<PlanetPlayer> getPlayers() {
        return players;
    }

}
