package me.devsnox.planetsystem.api.handler;

import me.devsnox.planetsystem.api.database.DatabasePlanet;
import me.devsnox.planetsystem.api.database.DatabasePlayer;

import java.util.UUID;

public interface DatabaseHandler {

    DatabasePlanet getPlanet(final UUID uuid);

    void savePlanet(final DatabasePlanet databasePlanet);

    void create(UUID planet, UUID player);

    DatabasePlayer getPlayer(final UUID uuid);

    void savePlayer(DatabasePlayer databasePlayer);

}
