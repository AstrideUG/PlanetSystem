package me.devsnox.planetsystem.core.commands.modules;

import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.log.Logger;
import me.devsnox.planetsystem.api.player.PlanetPlayer;
import me.devsnox.planetsystem.core.commands.PlanetCommandModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InviteCommand implements PlanetCommandModule {
    @Override
    public void execute(PlanetPlayer player, String[] args) {
        Logger logger = player.getLogger();
        String prefix = "Commands.Invite.Failed.";
        if (args.length == 1) {
            UUID uuid;
            try {
                uuid = UUID.fromString(args[0]);
            } catch (Exception ex) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    uuid = target.getUniqueId();
                } else {
                    logger.warn(prefix + "InNoUUIDAndTargetIsNotOnline");
                    return;
                }
            }

            if (!player.getPlanet().getMembers().contains(uuid)) {
                player.getPlanet().getMembers().add(uuid);
                logger.success("Commands.Invite.Player.Successes");
                Holder.Impl.holder.getPlayerData().getPlayer(player.getUUID()).getLogger().success("Commands.Invite.Target.Successes");
            } else logger.warn(prefix + "IsAlreadyAPlanetMember");
        } else logger.warn(prefix + "ArgsSizeIsNot1");
    }
}
