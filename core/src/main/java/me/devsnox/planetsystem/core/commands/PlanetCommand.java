package me.devsnox.planetsystem.core.commands;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.modules.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlanetCommand implements CommandExecutor {

    private final Map<String, PlanetCommandModule> commandModules;

    public PlanetCommand() {
        this.commandModules = new HashMap<>();
        this.commandModules.put("home", new HomeCommand());
        this.commandModules.put("info", new InfoCommand());
        this.commandModules.put("invite", new InviteCommand());
        this.commandModules.put("kick", new KickCommand());
        this.commandModules.put("sethome", new SethomeCommand());
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (commandSender instanceof Player) {
            final PlanetPlayer planetPlayer = Holder.Impl.holder.getPlayerData().getPlayer(((Player) commandSender).getUniqueId());

            if (args.length > 0) {
                if (this.commandModules.containsKey(args[0].toLowerCase())) {
                    this.commandModules.get(args[0].toLowerCase()).execute(planetPlayer, Arrays.copyOfRange(args, 1, args.length));
                } else {
                    planetPlayer.getLogger().info("command.args");
                }
            } else {
                planetPlayer.getLogger().info("command.args");
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "You must be a player!");
        }
        return false;
    }
}
