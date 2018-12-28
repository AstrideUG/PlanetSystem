package me.devsnox.planetsystem.core.log;

import me.devsnox.planetsystem.api.log.PlayerLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BasePlayerLogger implements PlayerLogger {

    private final Player player;

    public BasePlayerLogger(Player player) {
        this.player = player;
    }

    @Override
    public void log(Level level, Object... message) {
        String msg = Arrays.toString(message).substring(1, message.length - 1);
        switch (level) {
            case INFO:
                player.sendMessage(ChatColor.AQUA + msg + ".");
                break;
            case WARNING:
                player.sendMessage(ChatColor.RED + msg + "!");
                break;
            case SUCCESSFULLY:
                player.sendMessage(ChatColor.GREEN + msg + ".");
                break;
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
