package me.devsnox.planetsystem.core;

import me.devsnox.planetsystem.core.api.InternalHandler;
import me.devsnox.planetsystem.core.cache.PlanetCache;
import me.devsnox.planetsystem.core.cache.PlayerCache;
import me.devsnox.planetsystem.core.commands.PlanetCommand;
import me.devsnox.planetsystem.core.database.DatabaseHandler;
import me.devsnox.planetsystem.core.listeners.PlanetListener;
import me.devsnox.planetsystem.core.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class PlanetSystem extends JavaPlugin {

    private DatabaseHandler databaseHandler;

    private BaseInternalHandler baseInternalHandler;

    @Override
    public void onEnable() {
        this.baseInternalHandler = new BaseInternalHandler(this);

        this.registerListeners();
        this.registerCommands();

        this.getLogger().log(Level.INFO, "PlanetSystem started");
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "PlanetSystem stopped");
    }

    private void registerCommands() {
        this.getCommand("planet").setExecutor(new PlanetCommand());
    }

    private void registerListeners() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlanetListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
    }
}
