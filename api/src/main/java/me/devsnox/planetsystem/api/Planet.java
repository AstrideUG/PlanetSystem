package me.devsnox.planetsystem.api;

import javax.xml.stream.Location;
import java.util.List;
import java.util.UUID;

public interface Planet {

    Integer getId();

    Location getSpawnLocation();

    List<UUID> getMembers();

    Integer getLevel();
    Integer getTotalExp();
    Integer getExpToNextLevel();
}
