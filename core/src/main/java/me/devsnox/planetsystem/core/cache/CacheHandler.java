package me.devsnox.planetsystem.core.cache;

public class CacheHandler {

    private PlanetCache planetCache;
    private PlayerCache playerCache;

    public CacheHandler() {
        this.planetCache = new PlanetCache();
        this.playerCache = new PlayerCache();
    }

    public PlanetCache getPlanetCache() {
        return planetCache;
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }
}
