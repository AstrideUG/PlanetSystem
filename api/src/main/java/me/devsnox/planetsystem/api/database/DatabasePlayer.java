package me.devsnox.planetsystem.api.database;

import java.util.List;
import java.util.UUID;

public interface DatabasePlayer {

    UUID getUuid();

    UUID getPlanetUniqueId();

    List<UUID> getMemberedPlanets();

}
