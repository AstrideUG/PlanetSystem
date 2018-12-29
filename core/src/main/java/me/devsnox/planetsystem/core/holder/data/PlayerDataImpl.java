package me.devsnox.planetsystem.core.holder.data;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlayerData;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.database.DatabasePlayer;
import me.devsnox.planetsystem.core.player.BasePlanetPlayer;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.function.Consumer;

public final class PlayerDataImpl implements PlayerData {

    private final Holder holder;
    private final Set<PlanetPlayer> players;

    public PlayerDataImpl(final Holder holder) {
        this.holder = holder;
        this.players = new HashSet<>();
    }

    @Override
    public void load(final UUID uuid, final Consumer<PlanetPlayer> request) {
        System.out.println(request);
        final me.devsnox.planetsystem.api.database.DatabasePlayer databasePlayer = holder.getDatabaseHandler().getPlayer(uuid);
        System.out.println(databasePlayer);

        System.out.println(uuid);

        holder.getPlanetData().load(uuid, loadedPlanet -> {
            System.out.println(loadedPlanet);
            final List<Planet> members = new ArrayList<>();//TODO databasePlayer.getMemberedPlanets().stream().map(this::getPlanet).collect(Collectors.toList());
            System.out.println(uuid);
            System.out.println(Bukkit.getPlayer(uuid));
            System.out.println(members);
            final PlanetPlayer planetPlayer = new BasePlanetPlayer(Bukkit.getPlayer(uuid), loadedPlanet, members);

            System.out.println(planetPlayer);
            System.out.println(holder.getPlayerData().getPlayers());

            holder.getPlayerData().getPlayers().add(planetPlayer);
            System.out.println(holder.getPlayerData().getPlayers());
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
