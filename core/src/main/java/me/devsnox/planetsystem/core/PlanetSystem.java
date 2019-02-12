package me.devsnox.planetsystem.core;

import me.devsnox.planetsystem.api.planet.LoadedPlanet;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommand;
import me.devsnox.planetsystem.core.holder.HolderImpl;
import me.devsnox.planetsystem.core.listeners.PlanetListener;
import me.devsnox.planetsystem.core.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.logging.Level;

import static me.devsnox.planetsystem.api.holder.Holder.Impl.holder;

public class PlanetSystem extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        holder = new HolderImpl(this);
        this.registerListeners();
        this.registerCommands();
//        Logger.getLogger("org.mongodb").setLevel(Level.SEVERE);
//        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
//
//        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//        loggerContext.getLogger("org.mongodb.driver").setLevel(ch.qos.logback.classic.Level.OFF);
        this.getLogger().log(Level.INFO, "PlanetSystem started");
    }
    
    @Override
    public void onDisable()
    {
        this.saveAll();
        this.getLogger().log(Level.INFO, "PlanetSystem stopped");
    }
    
    public void saveAll()
    {
        final Set<LoadedPlanet> planets = holder.getPlanetData().getLoadedPlanets();
        final Set<PlanetPlayer> players = holder.getPlayerData().getPlayers();
        if (planets.size() != 0)
        {
            planets.forEach(planet -> holder.getPlanetData().save(planet.getOwnerUniqueID()));
        }
        if (players.size() != 0)
        {
            players.forEach(player -> holder.getPlayerData().save(player.getUUID()));
        }
    }
    
    private void registerCommands()
    {
        this.getCommand("planet").setExecutor(new PlanetCommand());
    }
    
    private void registerListeners()
    {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlanetListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
    }
}
