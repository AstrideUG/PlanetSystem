package me.devsnox.planetsystem.api;

import me.devsnox.planetsystem.api.location.PlanetLocation;

public class PlanetFactory {

    public static PlanetAPI planetAPI = null;

    public void test() {
        PlanetLocation.createFromBukkitLocation();
    }
}
