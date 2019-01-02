package me.devsnox.planetsystem.core.holder.data;

import lombok.Data;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.holder.data.PlayerData;
import me.devsnox.planetsystem.api.planet.Planet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.database.DatabasePlayer;
import me.devsnox.planetsystem.core.player.BasePlanetPlayer;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.function.Consumer;

@Data
public final class PlayerDataImpl implements PlayerData {

    private final Holder holder;
    private final Set<PlanetPlayer> players = new HashSet<>();

    @Override
    public void load(final UUID uuid, final Consumer<PlanetPlayer> request) {
//        final me.devsnox.planetsystem.api.database.DatabasePlayer databasePlayer = this.holder.getDatabaseHandler().getPlayer(uuid);

        this.holder.getPlanetData().load(uuid, loadedPlanet -> {
            final List<Planet> members = new ArrayList<>();//TODO databasePlayer.getMemberedPlanets().stream().map(this::getPlanet).collect(Collectors.toList());
            final PlanetPlayer planetPlayer = new BasePlanetPlayer(Bukkit.getPlayer(uuid), loadedPlanet, members);

            this.players.add(planetPlayer);
            request.accept(planetPlayer);
        });
    }

    @Override
    public void save(final UUID uuid) {
        final PlanetPlayer player = this.holder.getPlayerData().getPlayer(uuid);
        if (player == null) return;
        final DatabasePlayer databasePlayer = DatabasePlayer.by(player.getPlanet());
        this.holder.getDatabaseHandler().savePlayer(databasePlayer);
    }

    @Override
    public void unload(UUID uuid) {
        this.save(uuid);
        this.players.remove(this.getPlayer(uuid));
    }

}
