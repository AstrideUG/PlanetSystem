package me.devsnox.planetsystem.core.log;

import lombok.Data;
import me.devsnox.planetsystem.api.log.PlayerLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Data
public class BasePlayerLogger implements PlayerLogger {

    private final Player player;

    public BasePlayerLogger(final Player player) {
        this.player = player;
    }

    @Override
    public void log(final Level level, final Object... message) {
        final String msg = Arrays.toString(message);
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
