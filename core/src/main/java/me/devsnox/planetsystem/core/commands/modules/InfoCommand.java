package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;
import net.md_5.bungee.api.chat.TextComponent;

public class InfoCommand implements PlanetCommandModule {

    @Override
    public void execute(PlanetPlayer player, String[] args) {
        if(player.hasPlanet()) {
            if(player.getPlanet().isInside(player.getPlayer())) {
                //TODO: Add message handling
            } else {
                player.getLogger().log(""); //TODO: Add message-key
            }
        } else {
            player.getLogger().log(""); //TODO: Add message-key
        }
    }
}
