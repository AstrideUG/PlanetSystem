package me.devsnox.planetsystem.api.holder;

import me.devsnox.planetsystem.api.handler.DatabaseHandler;
import me.devsnox.planetsystem.api.handler.GridHandler;
import me.devsnox.planetsystem.api.holder.data.*;
import org.bukkit.World;

public interface Holder {

    DatabaseHandler getDatabaseHandler();

    World getWorld();

    PlayerData getPlayerData();

    PlanetData getPlanetData();

    GridHandler getGridHandler();

    class Impl {
        public static Holder holder = null;
    }

}
