package me.devsnox.planetsystem.api.planet;

import me.devsnox.planetsystem.api.player.PlanetMember;

import java.util.List;
import java.util.UUID;

public interface PlanetInfo {

    UUID getUniqueID();
    UUID getOwnerUniqueID();

    List<PlanetMember> getMembers();
}
